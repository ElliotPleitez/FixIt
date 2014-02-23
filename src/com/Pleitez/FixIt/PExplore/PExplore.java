package com.Pleitez.FixIt.PExplore;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

//public class PExplore extends JFileChooser {
public class PExplore {
    private Address address;
    private Content content;
    private ContentList contentList;
    
    public enum ContentHeader {
        Name {
            @Override
            public String toString() {
                return "Name";
            }
        },
        Size {
            @Override
            public String toString() {
                return "Size";
            }
        },
        EditDate {
            @Override
            public String toString() {
                return "Edit Date";
            }
        },
        CreationDate {
            @Override
            public String toString() {
                return "Creation Date";
            }
        },
        User {
            @Override
            public String toString() {
                return "User";
            }
        };
        
        public String toString() {
            return null;
        }
    }
    
    // Note: To be used when browsing through directories, relative to start
    public PExplore(String directoryRoot) {
        this.address = new Address();
        this.content = new Content(directoryRoot);

        this.content.addContentListener(address);
        this.address.addAddressListener(content);
    }
    
    public JPanel displayAddress() {
        return address;
    }
    
    public JPanel displayContent() {
        return content;
    }
    
    // Note: To be used when creating virtual directories
    public PExplore(ArrayList<ContentHeader> contentList) {
        this.contentList = new ContentList(contentList);
    }
    
    public void UpdateContent(ArrayList<ContentHeader> contentList) {
        this.contentList.UpdateContent(contentList);
    }
    
    public JPanel displayContentList() {
        return contentList;
    }
    
    public void setSelectionMode(ListSelectionModel selectionModel) {
        // content.contentTable.setSelectionMode((ListSelectionModel)selectionModel);
        content.contentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        content.contentTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        content.contentTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
    }
    
    public void viewFolders() {
        //content.viewFolders();
    }
    
    public void viewFiles() {
        //content.viewFiles();
    }
    
    public void renameFiles() {
        //content.renameFiles();
    }
    
    public void viewAll() {
        //content.viewAll();
    }
}
