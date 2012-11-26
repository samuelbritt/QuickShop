""" Testing graphs and path finding algorithms """

import networkx as nx
import matplotlib.pyplot as plt
from functools import total_ordering

@total_ordering
class AisleGraphPrimalNode(object):
    """ Nodes in the primal (directed) aisle graph. """

    def __init__(self, x, y, sentinal=None):
        """ x, y are coordinates in AisleGraph. Set `sentinal` to True if this
        is a sentinal node. """
        self.x = x
        self.y = y
        assert sentinal in (None, "source", "sink")
        self.sentinal = sentinal
        self.label = sentinal or "{}, {}".format(self.x, self.y)
        self.adjacencies = []

    def add_adjacency(self, node):
        self.adjacencies.append(node)

    def __repr__(self):
        return "[{}]".format(self.label)

    # Make object hashable
    def __key(self):
        return (self.x, self.y)
    def __eq__(self, other):
        return self.__key() == other.__key()
    def __lt__(self, other):
        return self.__key() < other.__key()
    def __hash__(self):
        return hash(self.__key())

class AisleGraphSource(AisleGraphPrimalNode):
    def __init__(self):
        AisleGraphPrimalNode.__init__(self, -1, -1, sentinal="source")
        self.label = "s"
class AisleGraphSink(AisleGraphPrimalNode):
    def __init__(self):
        AisleGraphPrimalNode.__init__(self, -1, -1, sentinal="sink")
        self.label = "t"

@total_ordering
class AisleGraphPrimalEdge(object):
    """ Edge in the primal (directed) aisle graph. """

    def __init__(self, n1, n2):
        """ creates an edge from node n1 to node n2
        """
        self.start = n1
        self.end = n2
        self.is_aisle = self.start.x == self.end.x
        self.weight = self._weight()
        self.start.add_adjacency(self.end)

    def _weight(self):
        weight = 1
        if self.start.sentinal or self.end.sentinal:
            weight = 0
        return weight

    def __repr__(self):
        return repr((self.start, self.end))

    # Make object hashable
    def __key(self):
        return (self.start, self.end)
    def __eq__(self, other):
        return self.__key() == other.__key()
    def __lt__(self, other):
        return self.__key() < other.__key()
    def __hash__(self):
        return hash(self.__key())

@total_ordering
class AisleGraphDualNode(object):
    """ Nodes in the linear dual aisle graph. """
    def __init__(self, primal_edge):
        """ creates a dual node corresponding to a primal_edge """
        self.edge = primal_edge
        self.is_aisle = self.edge.is_aisle

    def primal_edge(self):
        """ returns primal edge corresponding to this node """
        return self.edge

    def __repr__(self):
        return repr(self.edge)

    # Make object hashable
    def __key(self):
        return self.edge
    def __eq__(self, other):
        return self.__key() == other.__key()
    def __lt__(self, other):
        return self.__key() < other.__key()
    def __hash__(self):
        return hash(self.__key())

class DualAngles(object):
    """ enum for angles in dual graph """
    ZERO = 0
    PI2 = 1
    PI = 2

@total_ordering
class AisleGraphDualEdge(object):
    """ Edges in linear dual aisle graph. """

    def __init__(self, n1, n2):
        """ Creates a dual edge from node n1 to n2
        """
        self.start = n1
        self.end = n2
        self.angle = self._angle()
        self.weight = self._weight()

    def _angle(self):
        if self.start.is_aisle != self.end.is_aisle:
            # right angle
            return DualAngles.PI2
        elif self.start.start == self.end.end:
            # U-turn
            return DualAngles.PI
        else:
            return DualAngles.ZERO

    def _weight(self):
        return ((self.start.weight + self.end.weight) / 2. 
                + self._weight_of_angle(self.angle))

    def _weight_of_angle(self, dual_angle):
        """ returns weight associated with given angle """
        # favor right angles, penalize u-turns
        weights = {
            DualAngles.ZERO: 2,
            DualAngles.PI2: 1,
            DualAngles.PI: 5
        }
        return weights[dual_angle]

    def __repr__(self):
        return repr((self.start, self.end))

    # Make object hashable
    def __key(self):
        return (self.start, self.end)
    def __eq__(self, other):
        return self.__key() == other.__key()
    def __lt__(self, other):
        return self.__key() < other.__key()
    def __hash__(self):
        return hash(self.__key())


class AisleGraph(object):
    """ Graph for grocery store aisles """

    def __init__(self, aisle_count, nodes_per_aisle, start=(0,0), end=None):
        self.aisle_count = aisle_count
        self.nodes_per_aisle = nodes_per_aisle

        self.G = nx.DiGraph()
        self.create_graph(start, end)
        self.node = self.G.node
        self.edge = self.G.edge

    def edges(self, *args, **kwargs):
        return sorted(self.G.edges(*args, **kwargs))
    def nodes(self, *args, **kwargs):
        return sorted(self.G.nodes(*args, **kwargs))

    def create_graph(self, start, end=None):
        self.create_aisle(0)
        for x in range(1, self.aisle_count):
            self.create_aisle(x)
            self.connect_aisles(x - 1, x)

        end = end or (self.aisle_count - 1, 0)
        self.add_sentinals(start, end)
        self.add_node_labels()

    def create_aisle(self, x_coord):
        """ creates an aisle of nodes at x coordinate x_coord. 
        """
        n1 = AisleGraphPrimalNode(x_coord, 0)
        for y in range(1, self.nodes_per_aisle):
            n2 = AisleGraphPrimalNode(x_coord, y)
            self.create_double_edge(n1, n2)
            n1 = n2

    def connect_aisles(self, x1, x2):
        """ Connects top and bottom nodes of aisles at x1 and x2 """
        for y in (0, self.nodes_per_aisle - 1):
            n1 = AisleGraphPrimalNode(x1, y)
            n2 = AisleGraphPrimalNode(x2, y)
            assert (n1 in self.nodes())
            assert (n2 in self.nodes())
            self.create_double_edge(n1, n2)

    def create_double_edge(self, n1, n2):
        """ creates edges (n1, n2) and (n2, n1) """
        forward = n1, n2
        reverse = n2, n1
        for direction in (forward, reverse):
            self.create_single_edge(*direction)

    def create_single_edge(self, n1, n2):
            e = AisleGraphPrimalEdge(n1, n2)
            w = e.weight
            self.G.add_edge(n1, n2, object=e, weight=w, label=w)

    def add_sentinals(self, start, end):
        """ adds the dummy source node going to `start`, and the dummy sink
        node going to `end` """
        self.source = AisleGraphSource()
        self.sink = AisleGraphSink()

        start = AisleGraphPrimalNode(*start)
        end = AisleGraphPrimalNode(*end)
        for edge in ((self.source, start), (end, self.sink)):
            self.create_single_edge(*edge)

    def add_node_labels(self):
        for node in self.G.nodes():
            self.G.node[node].update(label=repr(node))

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
            n = A.get_node(node)
            n.attr.update(pos = "{},{}!".format(node.x, node.y))
        A.draw(filename, prog='neato')


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
        for edge in self.aisle_graph.edges(data=True):
            primal_edge = edge[2]['object']
            dual_node = AisleGraphDualNode(primal_edge)
            self.dual_graph.add_node(dual_node, label=str(dual_node))

    def add_dual_edges(self):
        for first_dual_node in self.dual_graph.nodes():
            primal_edge_1 = first_dual_node.primal_edge()
            end_node = primal_edge_1.end
            for adj in end_node.adjacencies:
                primal_edge_2 = AisleGraphPrimalEdge(end_node, adj)

                second_dual_node = \
                        self.dual_graph.node[AisleGraphDualNode(primal_edge_2)]
                dual_edge = AisleGraphDualEdge(first_dual_node, second_dual_node)
                self.dual_graph.add_edge(first_dual_node, second_dual_node,
                                         object=dual_edge,
                                         weight=dual_edge.weight)

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

        A.graph_attr.update(dpi = 250,
                            overlap = False,
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

    # A = D.draw('D.png')

    # for i, (f, l) in enumerate(H.edges()):
    #     H.edge[f][l]['id'] = i

    # D = to_dual(G)
    # draw(D, 'D.png')
