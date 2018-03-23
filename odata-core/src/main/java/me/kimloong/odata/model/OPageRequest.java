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

import com.google.common.base.MoreObjects;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * OData分页请求
 *
 * @author KimLoong
 */
public class OPageRequest<T> implements Pageable {

    private static final int MAX_LIMIT = 100;

    private final Class<T> entityClass;

    /**
     * 偏移量
     */
    private int skip;

    /**
     * 分页记录数
     */
    private int top = 20;

    /**
     * 排序
     */
    private Sort sort;

    /**
     * 是否统计总数
     */
    private boolean count;

    public OPageRequest(Class<T> entityClass) {
        super();
        this.entityClass = entityClass;
    }

    public OPageRequest<T> addSort(Sort sort) {

        if (null == this.sort) {
            this.sort = sort;
        } else {
            this.sort = this.sort.and(sort);
        }
        return this;
    }

    public OPageRequest<T> defaultSort(Sort sort) {
        this.sort = MoreObjects.firstNonNull(this.sort, sort);
        return this;
    }

    public OPageRequest<T> forceSort(Sort sort) {

        this.sort = sort;
        return this;
    }

    @Override
    public int getPageNumber() {
        return skip / top;
    }

    @Override
    public int getPageSize() {
        return top;
    }

    @Override
    public int getOffset() {
        return skip;
    }

    public void setSkip(int skip) {
        if (skip < 0) {
            return;
        }
        this.skip = skip;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        if (top < 0 || top > MAX_LIMIT) {
            return;
        }
        this.top = top;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    @Override
    public Pageable next() {
        OPageRequest sp = new OPageRequest<>(this.getEntityClass());
        sp.setCount(this.count);
        sp.setSkip(skip + top);
        sp.setTop(top);
        sp.setSort(sort);
        return sp;
    }

    @Override
    public Pageable previousOrFirst() {
        if (skip < top) {
            return first();
        }
        OPageRequest sp = new OPageRequest<>(this.getEntityClass());
        sp.setCount(this.count);
        sp.setSkip(skip - top);
        sp.setTop(top);
        sp.setSort(sort);
        return sp;
    }

    @Override
    public Pageable first() {
        OPageRequest sp = new OPageRequest<>(this.getEntityClass());
        sp.setCount(this.count);
        sp.setSkip(0);
        sp.setTop(top);
        sp.setSort(sort);
        return sp;
    }

    @Override
    public boolean hasPrevious() {
        return skip > top;
    }
}
