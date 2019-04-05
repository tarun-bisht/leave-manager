/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_classes;

import java.util.prefs.Preferences;

/**
 *
 * @author Tarun Bisht
 */
public class read_write 
{
    
    public void Write(String[] Key,String Content[])
    {
        Preferences pref=Preferences.userRoot().node(this.getClass().getName());
        for(int i=0;i<Key.length && i<Content.length;i++)
        {
            pref.put(Key[i],Content[i]);
        }
    }
    public String[] Read(String key[])
    {
        String[] content =new String[key.length];
        Preferences pref=Preferences.userRoot().node(this.getClass().getName());
        for(int i=0;i<key.length;i++)
        {
            content[i]=pref.get(key[i],"");
        }
        return content;
    }
}
