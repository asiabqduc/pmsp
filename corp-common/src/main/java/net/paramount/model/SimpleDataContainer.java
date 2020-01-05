/**
 * 
 */
package net.paramount.model;

import java.util.List;

import lombok.Builder;
import net.paramount.common.ListUtility;
import net.paramount.model.base.DataList;
import net.paramount.model.base.IDataContainer;

/**
 * @author bqduc
 *
 */
@Builder
public class SimpleDataContainer implements IDataContainer<String> {
	@Builder.Default
	private List<String> headerItems = ListUtility.createArrayList();

	@Builder.Default
	private DataList<List<String>> dataItems = ListUtility.createDataList();

	@Override
	public DataList<List<String>> getDataItems() {
		return this.dataItems;
	}

	@Override
	public List<String> getHeaderItems() {
		return this.headerItems;
	}

	public void addHeaderItems(List<String> headerItems){
		this.headerItems.addAll(headerItems);
	}

	public void addDataItems(List<String> dataItems){
		this.dataItems.add(dataItems);
	}
}
