package me.akamex.luckapi.sidebar;

import java.util.List;

public abstract class AbstractSidebar implements Sidebar {

    protected String name;
    protected List<SidebarRow> rows;

    protected AbstractSidebar(String name, List<SidebarRow> rows) {
        this.name = name;
        this.rows = rows;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<SidebarRow> getRows() {
        return rows;
    }
}
