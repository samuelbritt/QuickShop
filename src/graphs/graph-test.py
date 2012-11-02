""" Testing graphs and path finding algorithms """

import networkx as nx
import matplotlib.pyplot as plt

def find_path(graph, item_locations, start_locations=None, end_locations=None):
    pass

if __name__ == '__main__':
    aisle_count = 4
    nodes_per_aisle = 4

    G = nx.grid_2d_graph(aisle_count, aisle_count)
    # remove generated edges between mid-aisle nodes
    for i in range(aisle_count - 1):
        for j in range(1, nodes_per_aisle - 1):
            G.remove_edge((i,j), (i+1,j))

    f = plt.figure()
    ax = f.add_subplot(111)
    # fix a regular grid by using the node names as grid coordinates
    nx.draw(G, pos=dict(zip(G,G)), ax=ax)
    plt.show()

    items = [(0,0), (2,3), (1,1)]
    start = [(0,0),]

    path = find_path(G,
                     item_locations=items,
                     start_locations=start,
                     end_locations=None)
