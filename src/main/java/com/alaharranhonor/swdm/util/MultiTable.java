package com.alaharranhonor.swdm.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class MultiTable<R, C, V> implements Table<R, C, List<V>> {

    public static <R, C, V> MultiTable<R, C, V> create() {
        return new MultiTable<>();
    }

    private final HashBasedTable<R, C, List<V>> table;

    private MultiTable() {
        this.table = HashBasedTable.create();
    }

    @Override
    public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
        return this.table.contains(rowKey, columnKey);
    }

    @Override
    public boolean containsRow(@Nullable Object rowKey) {
        return this.table.containsRow(rowKey);
    }

    @Override
    public boolean containsColumn(@Nullable Object columnKey) {
        return this.table.containsColumn(columnKey);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        for (Cell<R, C, List<V>> cell : this.table.cellSet()) {
            if (cell.getValue().contains(value)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<V> get(@Nullable Object rowKey, @Nullable Object columnKey) {
        return this.table.get(rowKey, columnKey);
    }

    @Override
    public boolean isEmpty() {
        return this.table.isEmpty();
    }

    @Override
    public int size() {
        return this.table.size();
    }

    @Override
    public void clear() {
        this.table.clear();
    }

    @Nullable
    @Override
    public List<V> put(R rowKey, C columnKey, List<V> values) {
        return this.table.put(rowKey, columnKey, values);
    }

    public boolean add(R rowKey, C columnKey, V value) {
        if (this.contains(rowKey, columnKey)) {
            this.get(rowKey, columnKey).add(value);
            return false;
        }

        List<V> values = new ArrayList<>() {{
            add(value);
        }};
        this.put(rowKey, columnKey, values);
        return true;
    }

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends List<V>> table) {
        this.table.putAll(table);
    }

    @Nullable
    @Override
    public List<V> remove(@Nullable Object rowKey, @Nullable Object columnKey) {
        return this.table.remove(rowKey, columnKey);
    }

    @Override
    public Map<C, List<V>> row(R rowKey) {
        return this.table.row(rowKey);
    }

    @Override
    public Map<R, List<V>> column(C columnKey) {
        return this.table.column(columnKey);
    }

    @Override
    public Set<Cell<R, C, List<V>>> cellSet() {
        return this.table.cellSet();
    }

    @Override
    public Set<R> rowKeySet() {
        return this.table.rowKeySet();
    }

    @Override
    public Set<C> columnKeySet() {
        return this.columnKeySet();
    }

    @Override
    public Collection<List<V>> values() {
        return this.table.values();
    }

    public Collection<V> items() {
        return this.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public Map<R, Map<C, List<V>>> rowMap() {
        return this.table.rowMap();
    }

    @Override
    public Map<C, Map<R, List<V>>> columnMap() {
        return this.table.columnMap();
    }
}
