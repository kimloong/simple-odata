[TOC]
# Simple OData
该项目是简化版，易于接入使用的OData实现。

我们在设计Restful接口时，当于遇到复杂、多变、组合列表查询时，往往需要进行代码变更，而且很容易设计出风格迥异的接口。
由于`OData`规范的强大及成熟，可以设计出灵活的查询，刚好能够解决以上问题。而现存针对Java的框架实现(如`Olingo`)使用上却比较难(个人观点)，且我们在大部分的项目中并不需要这么强大灵活，因此有了这个轮子。

# 说明
* JDK >= 1.8
* 以下规范写有 **待支持** 的表示尚未支持，未来也有可能不进行支持

# Getting Start

# 查询选项
## `$filter` 过滤
```
http://host/service/Products?$filter=Price lt 10.00
```

### 比较操作符
* `eq` 等于(Equal)
```
City eq 'Redmond'
```

* `ne` 不等于(Not equal)
```
City ne 'London'
```

* `gt` 大于(Greater than)
```
Price gt 20
```

* `ge` 大于等于(Greater than or equal)
```
Price ge 20
```

* `lt` 小于(Less than)
```
Price lt 20
```

* `le` 小于等于(Less than or equal)
```
Price le 20
```

* `has` Has flags

待支持

不理解这个跟`eq`的差别,以下为规范全文
> The has operator returns true if the right hand operand is an enumeration
> value whose flag(s) are set on the left operand.
> The null value is treated as unknown, so if one operand evaluates to null,
> the has operator returns null.
```
Style has Sales.Color'Yellow'
```

### 分组操作符
* `()` 分组(Precedence grouping)

待支持

最高优先级
```
City eq 'Redmond' and (Price le 3.5 or Price gt 200)
```

### 逻辑操作符
优化级高到低: `not`>`and`>`or`

* `not` 逻辑非(Logical negation)

待支持

* `and` 逻辑与(Logical and)
```
Price le 200 and Price gt 3.5
```

* `or` 逻辑或(Logical or)
```
Price le 3.5 or Price gt 200
```

## `$search`

待支持

搜索(由服务端指定的一个或多个字段),当同时出现`$search`与`$filter`时，两者须同时满足(`and`)
* 词语匹配(match term)
```
http://host/service/Products?$search=bike
```

* 短语匹配(match phrase)

短语使用双引号(double-quotes)包围
```
http://host/service/Products?$search="mountain bike"
```

* 组合匹配

规范要求各逻辑操作符使用大写
```
http://host/service/Products?$search=mountain OR bike
http://host/service/Products?$search=mountain AND bike
http://host/service/Products?$search=NOT clothing
http://host/service/Products?$search=(mountain OR bike) AND NOT clothing
```

## `$count`
是否统计符合条件记录数
```
http://host/service/Products?$count=true
```

## `$select` 投影
待支持

* 默认投影全部字段,也可以显示指定
```
http://host/service/Products?$select=*
```
* 部分投影
```
http://host/service/Customers?$select=Address,Orders
```

## `$orderby` 排序
默认升序,逗号间隔每个排序
以下两者等价
```
http://host/service/Products?$orderby=ReleaseDate, Rating desc
http://host/service/Products?$orderby=ReleaseDate asc, Rating desc
```

## `$skip`
跳过n行数据，接收非负数

## `$top`
取前几行数据（基于`$skip`后）

## `$expand` 展开字段

待支持

将查询实体的某些字段展开查询，多个使用逗号间隔
```
http://host/service.svc/Customers?$expand=Orders
```

## 取消分页

待支持

# 模块设计
* `odata-core`核心模块
  * 基础POJO
  * 基础接口

* `odata-parser-string`基于字符串解析查询选项

* `odata-parser-antlr`基于语法解析查询选项
  * 待设计

* `odata-spring-mvc`与Spring Mvc整合
  * `HandlerMethodArgumentResolver`

* `odata-spring-data-jpa`与Spring Data JPA整合

* `odata-spring-data-mongo`与Spring Data Mongo整合

* `odata-querydsl`与Querydsl整合
  * 待设计

# 待完成
1. 使用词法解析的方式来解析过滤条件
2. `ItemList`的`items`是否可以使用更抽象的集合结构

# LICENSE
Apache License 2.0

# 参考
> [odata规范](http://docs.oasis-open.org/odata/odata/v4.0/odata-v4.0-part1-protocol.html)

> [odata规范堪误](http://docs.oasis-open.org/odata/odata/v4.0/errata03/os/complete/part2-url-conventions/odata-v4.0-errata03-os-part2-url-conventions-complete.html)

> [How to navigate an OData compliant service](https://blogs.msdn.microsoft.com/alexj/2009/11/18/tip-44-how-to-navigate-an-odata-compliant-service/)