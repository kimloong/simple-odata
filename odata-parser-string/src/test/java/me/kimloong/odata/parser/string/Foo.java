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

package me.kimloong.odata.parser.string;

import me.kimloong.odata.annotation.FieldMapping;

import java.util.Date;

/**
 * 测试实体类 全特性
 *
 * @author KimLoong
 */
@FieldMapping(field = "field", mapping = "mapping", type = String.class)
@FieldMapping(field = "notInEntityField", mapping = "notInEntityMapping", type = String.class)
public class Foo extends BaseFoo {

    private boolean primitiveBoolean;
    private Boolean boxBoolean;
    private int primitiveInt;
    private Integer boxInt;
    private float primitiveFloat;
    private Float boxFloat;
    private double primitiveDouble;
    private Double boxDouble;
    private long primitiveLong;
    private Long boxLong;
    private char charField;
    private String string;
    private Date date;
    private Type enumField;
    private String mapping;
    private String field1;
    private String field2;

    public boolean isPrimitiveBoolean() {
        return primitiveBoolean;
    }

    public void setPrimitiveBoolean(boolean primitiveBoolean) {
        this.primitiveBoolean = primitiveBoolean;
    }

    public Boolean getBoxBoolean() {
        return boxBoolean;
    }

    public void setBoxBoolean(Boolean boxBoolean) {
        this.boxBoolean = boxBoolean;
    }

    public int getPrimitiveInt() {
        return primitiveInt;
    }

    public void setPrimitiveInt(int primitiveInt) {
        this.primitiveInt = primitiveInt;
    }

    public Integer getBoxInt() {
        return boxInt;
    }

    public void setBoxInt(Integer boxInt) {
        this.boxInt = boxInt;
    }

    public float getPrimitiveFloat() {
        return primitiveFloat;
    }

    public void setPrimitiveFloat(float primitiveFloat) {
        this.primitiveFloat = primitiveFloat;
    }

    public Float getBoxFloat() {
        return boxFloat;
    }

    public void setBoxFloat(Float boxFloat) {
        this.boxFloat = boxFloat;
    }

    public double getPrimitiveDouble() {
        return primitiveDouble;
    }

    public void setPrimitiveDouble(double primitiveDouble) {
        this.primitiveDouble = primitiveDouble;
    }

    public Double getBoxDouble() {
        return boxDouble;
    }

    public void setBoxDouble(Double boxDouble) {
        this.boxDouble = boxDouble;
    }

    public long getPrimitiveLong() {
        return primitiveLong;
    }

    public void setPrimitiveLong(long primitiveLong) {
        this.primitiveLong = primitiveLong;
    }

    public Long getBoxLong() {
        return boxLong;
    }

    public void setBoxLong(Long boxLong) {
        this.boxLong = boxLong;
    }

    public char getCharField() {
        return charField;
    }

    public void setCharField(char charField) {
        this.charField = charField;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getEnumField() {
        return enumField;
    }

    public void setEnumField(Type enumField) {
        this.enumField = enumField;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    enum Type {
        A,
        B,
    }
}
