package com.example.quickshop.graphs;

/** Interface for creating IGraphCategories
 * 
 * @author sam
 *
 */
public interface IGraphCategoryFactory<T extends IGraphCategory> {
	public T makeGraphCategory(String catName);
}
