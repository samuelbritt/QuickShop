""" Testing graphs and path finding algorithms """

import networkx as nx
import matplotlib.pyplot as plt

class AisleGraph(object):
    """ Graph for grocery store aisles """

    def __init__(self, aisle_count, nodes_per_aisle):
        self.U = nx.grid_2d_graph(aisle_count, nodes_per_aisle)
        # remove generated edges between mid-aisle nodes
        for i in range(aisle_count - 1):
            for j in range(1, nodes_per_aisle - 1):
                self.U.remove_edge((i,j), (i+1,j))
        self.G = self.U.to_directed()

    def draw(self, filename, node_labels=True):
        A = nx.to_agraph(self.G)
        if node_labels:
            A.node_attr['shape'] = 'circle'
            A.node_attr['width'] = '.3'
        else:
            A.node_attr['shape'] = 'point'
        A.node_attr['fontsize'] = '10'
        A.node_attr['fixedsize'] = True
        for node in self.G:
            n = A.get_node(node)
            n.attr['pos'] = "{},{}!".format(node[0], node[1])
            n.attr['label'] = "{}{}".format(node[0], node[1])
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
        # self.add_dual_edges()

    def add_dual_nodes(self):
        for edge in self.aisle_graph.edges():
            self.add_dual_node(edge)

    def add_dual_node(self, edge):
        s, t = edge
        label = "{}{}{}{}".format(s[0], s[1], t[0], t[1])
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
        print s, t
        label = "{}{}{}{}{}{}".format(
            s[0][0], s[0][1],
            s[1][0], s[1][1], # = t[0][0], t[0][1]
            t[1][0], t[1][1]
        )
        print label
        self.dual_graph.add_edge(s, t, label=label)

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

    # for i, (f, l) in enumerate(H.edges()):
    #     H.edge[f][l]['id'] = i

    # D = to_dual(G)
    # draw(D, 'D.png')
