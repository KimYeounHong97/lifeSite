package com.life.site.config.param;

import java.util.List;
import java.util.Map;

public class CommonTreeVo {
    
    private String text;
    private String id;
    private String cls;
    private String expanded;
    private List<Map<String, Object>> children;
    private String leaf;
    private String checked;
    public int sub;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public Boolean getExpanded() {
        if(expanded == null || expanded.equals("")){
            return null;
        }
        else{
            return Boolean.valueOf(expanded);
        }
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }

    public Boolean getLeaf() {
        if(leaf == null || leaf.equals("")){
            return null;
        }
        else{
            return Boolean.valueOf(leaf);
        }
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public Boolean getChecked() {
        if(checked == null || checked.equals("")){
            return null;
        }
        else{
            return Boolean.valueOf(checked);
        }
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
    
    public List<Map<String, Object>> getChildren() {
        if(children == null || children.size() == 0){
            return null;
        }
        else{
            return children;
        }
    }

    public void setChildren(List<Map<String, Object>> children) {
        this.children = children;
    }

}