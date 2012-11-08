""" Testing graphs and path finding algorithms """

import networkx as nx
import matplotlib.pyplot as plt

class AisleGraph(object):
    """ Graph for grocery store aisles """

    def __init__(self, aisle_count, nodes_per_aisle, start=(0,0), end=None):
        self.aisle_count = aisle_count
        self.nodes_per_aisle = nodes_per_aisle

        self.U = nx.grid_2d_graph(aisle_count, nodes_per_aisle)
        # remove generated edges between mid-aisle nodes
        for i in range(aisle_count - 1):
            for j in range(1, nodes_per_aisle - 1):
                self.U.remove_edge((i,j), (i+1,j))

        end = end or (aisle_count - 1, 0)
        self.G = self.U.to_directed()
        self.source = self.sink = None
        self.add_sentinals(start, end)
        self.add_node_labels()
        self.add_edge_weights()
        self.node = self.G.node
        self.edge = self.G.edge

    def add_edge_weights(self):
        for u,v in self.G.edges():
            w = self.edge_weight(u,v)
            self.G[u][v].update(weight=w, label=w)

    def add_node_labels(self):
        for node in self.G.nodes():
            if self.is_sentinal(node):
                self.G.node[node].update(label=node)
            else:
                self.G.node[node].update(label="{}{}".format(node[0], node[1]))

    def edge_weight(self, u, v):
        """ returns weight of edge between nodes u and v """
        weight = 0
        if self.is_sentinal(u, v):
            weight = 0
        elif self.co_aisle(u, v):
            weight = 1
        else:
            weight = 2
        return weight

    def add_sentinals(self, start, end):
        """ adds the dummy source node going to `start`, and the dummy sink
        node going to `end` """
        self.source = "ss"
        self.sink = "tt"
        for edge in ((self.source, start), (end, self.sink)):
            self.G.add_edge(*edge, weight=0)


    def is_sentinal(self, *nodes):
        """ returns True if any node in `node_list` is a sentinal """
        sentinals = (self.source, self.sink)
        for n in nodes:
            if n in sentinals:
                return True
        return False

    def co_aisle(self, u, v):
        """ returns True if nodes u and v are on the same aisle """
        return not self.is_sentinal(u,v) and u[0] == v[0]
        

    def draw(self, filename, node_labels=True):
        A = nx.to_agraph(self.G)
        if node_labels:
            A.node_attr.update(shape = 'circle',
                               width = 0.3,
                               fontsize = 10,
                               fixedsize = True)
        else:
            A.node_attr['shape'] = 'point'
        for node in self.G:
            if not self.is_sentinal(node):
                n = A.get_node(node)
                n.attr.update(pos = "{},{}!".format(node[0], node[1]))
        A.draw(filename, prog='neato')

    def edges(self, *args, **kwargs):
        return sorted(self.G.edges(*args, **kwargs))
    def nodes(self, *args, **kwargs):
        return sorted(self.G.nodes(*args, **kwargs))


class DualGraph(object):
    """ Dual grocery store aisle graph"""

    def __init__(self, aisle_graph):
        self.aisle_graph = aisle_graph
        self.dual_graph = None
        self.aisle_to_dual()

    def aisle_to_dual(self):
        """ Converts an aisle graph to its linear dual """
        self.dual_graph = nx.DiGraph()
        self.add_dual_nodes()
        self.add_dual_edges()

    def add_dual_nodes(self):
        for edge in self.aisle_graph.edges():
            self.add_dual_node(edge)

    def add_dual_node(self, primal_edge):
        s, t = primal_edge
        label = "[{}{}]".format(
            self.aisle_graph.node[s]['label'],
            self.aisle_graph.node[t]['label']
        )
        self.dual_graph.add_node((s,t), label=label)

    def add_dual_edges(self):
        for node_0  in self.dual_graph.nodes():
            for node_1 in self.dual_graph.nodes():
                if self.are_adjacent(node_0, node_1):
                    self.add_dual_edge(node_0, node_1)

    def are_adjacent(self, n0, n1):
        """ returns True if `n0` and `n1` are "adjacent," meaning that the
        nodes correpsond to edges in the primal graph that are consective. """
        return n0[1] == n1[0]

    def add_dual_edge(self, s, t):
        start, mid, end = self.edge_to_primal_nodes((s, t))
        label = "({}{}{})".format(
            self.aisle_graph.node[start]['label'],
            self.aisle_graph.node[mid]['label'],
            self.aisle_graph.node[end]['label'],
        )
        weight = self.dual_edge_weight(s, t)
        self.dual_graph.add_edge(s, t,
                                 label=(label + str(weight)),
                                 weight=weight)

    def dual_edge_weight(self, s, t):
        """ returns the weight of a dual edge, based on the corresponding
        primal edge weights """
        ws = self.node_to_primal_edge(s)[-1]['weight']
        wt = self.node_to_primal_edge(t)[-1]['weight']
        return (ws + wt) / 2.

    def node_to_primal_edge(self, dual_node):
        """ returns the edge (with data) in the primal graph that corresponds
        to the dual node """
        s, t = dual_node
        return s, t, self.aisle_graph.edge[s][t]

    def edge_to_primal_nodes(self, dual_edge):
        """ returns the three primal nodes (with data) that corresond to a
        single dual edge """
        s, t = dual_edge
        start, mid0, attr = self.node_to_primal_edge(s)
        mid1, end, attr = self.node_to_primal_edge(t)
        assert mid0 == mid1
        return start, mid0, end

    def draw(self, filename, node_labels=True):
        A = nx.to_agraph(self.dual_graph)
        A.edge_attr.update(fontsize = 9,
                           arrowhead = "vee",
                           constraint = False)

        A.graph_attr.update(overlap = False,
                            splines = True)
        if node_labels:
            A.node_attr.update(shape = 'circle',
                               width = .4,
                               fontsize = 9,
                               fixedsize = True,)
        else:
            A.node_attr['shape'] = 'point'

        A.draw(filename, prog='neato')
        return A

    def node_aisle(self, node):
        """ returns the alise that a dual node is "on". Returns None if it is
        not on an aisle. """
        # s and t are the start and endpoints of the corresponding primal edge
        s, t  = node
        return s[0] if s[0] == t[0] else None


    def nodes(self, *args, **kwargs):
        return sorted(self.dual_graph.nodes(*args, **kwargs))
    def edges(self, *args, **kwargs):
        return sorted(self.dual_graph.edges(*args, **kwargs))

if __name__ == '__main__':
    aisle_count = 3
    nodes_per_aisle = 4

    G = AisleGraph(aisle_count, nodes_per_aisle)

    # fix a regular grid by using the node names as grid coordinates
    # node_positions = dict(zip(G,G))

    G.draw('G.png')

    D = DualGraph(G)
    A = D.draw('D.png')

    # for i, (f, l) in enumerate(H.edges()):
    #     H.edge[f][l]['id'] = i

    # D = to_dual(G)
    # draw(D, 'D.png')
