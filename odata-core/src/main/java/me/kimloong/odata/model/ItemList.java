/*
 *    Copyright 2018 KimLoong
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package me.kimloong.odata.model;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.Function;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * List规范的列表结构
 *
 * @param <T>
 * @author KimLoong
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemList<T> implements Iterable<T> {

    private static final ItemList EMPTY_ITEM_LIST = of(Collections.emptyList(), 0);

    private Integer count;

    /**
     * 需要额外添加的实体，序列化后与items在同一层级
     */
    @JsonUnwrapped
    private Object appendObj;

    /**
     * 需要额外添加的Map，序列化后与items在同一层级
     */
    private Map<String, Object> appendMap;

    private List<T> items = Collections.emptyList();

    /**
     * 使用传入的List进行构建Items
     */
    public static <T> ItemList<T> of(@Nonnull List<T> items) {
        ItemList<T> result = new ItemList<>();
        result.items = items;
        return result;
    }

    /**
     * 使用传入的List进行构建Items，并重新指定count
     */
    public static <T> ItemList<T> of(@Nonnull List<T> items, int count) {
        ItemList<T> result = of(items);
        result.count = count;
        return result;
    }

    /**
     * 使用传入的List进行构建ItemList，使用传入的page进行分页，count使用传入的list的size进行取值
     */
    public static <T> ItemList<T> subOf(@Nonnull List<T> items, OPageRequest<T> page) {
        int count = items.size();
        return subOf(items, count, page);
    }

    /**
     * 使用传入的List进行构建ItemList，使用传入的page进行分页，count使用传入的count进行取值
     */
    public static <T> ItemList<T> subOf(@Nonnull List<T> items, int count, OPageRequest<T> page) {
        int start = Math.min(page.getOffset(), count);
        int end = Math.min(page.getTop() + start, count);
        List<T> subList = items.subList(start, end);
        if (page.isCount()) {
            return ItemList.of(subList, count);
        }
        return ItemList.of(subList);
    }

    /**
     * 构建空的ItemList
     */
    @SuppressWarnings("unchecked")
    public static <T> ItemList<T> empty() {
        return (ItemList<T>) EMPTY_ITEM_LIST;
    }

    public Integer getCount() {
        return count;
    }

    public Object getAppendObj() {
        return appendObj;
    }

    public void setAppendObj(Object appendObj) {
        this.appendObj = appendObj;
    }

    @JsonAnyGetter
    public Map<String, Object> getAppendMap() {
        return appendMap;
    }

    @JsonAnySetter
    public void setAppendMap(Map<String, Object> appendMap) {
        this.appendMap = appendMap;
    }

    public List<T> getItems() {
        return items;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return this.items.size();
    }

    public <E> ItemList<E> map(@Nonnull Function<? super T, E> function) {
        ItemList<E> target = new ItemList<>();
        target.appendObj = this.appendObj;
        target.appendMap = this.appendMap;
        target.count = this.count;
        List<E> targetItems = new ArrayList<>(this.items.size());
        for (T item : items) {
            E targetItem = function.apply(item);
            targetItems.add(targetItem);
        }
        target.items = targetItems;
        return target;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return this.items.iterator();
    }
}
