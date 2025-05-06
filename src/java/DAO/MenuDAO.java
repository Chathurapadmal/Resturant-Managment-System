package DAO;

import model.MenuItem;
import java.util.List;

public interface MenuDAO {
    void addMenuItem(MenuItem item);
    void updateMenuItem(MenuItem item);
    void deleteMenuItem(int id);
    MenuItem getMenuItemById(int id);
    List<MenuItem> getAllMenuItems();
}
