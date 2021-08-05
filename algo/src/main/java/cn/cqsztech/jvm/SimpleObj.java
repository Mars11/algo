package cn.cqsztech.jvm;

import java.util.Objects;

/**
 * ccmars
 * 2021/6/8
 **/
public class SimpleObj {
    int i;
    int j;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleObj simpleObj = (SimpleObj) o;
        return i == simpleObj.i &&
                j == simpleObj.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
