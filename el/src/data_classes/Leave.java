/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_classes;


/**
 *
 * @author Tarun Bisht
 */
public class Leave 
{
    public int Total_Leave;
    public int Leave_6;
    public int Leave_12;
    
    public Leave(int tl,int l6,int l12)
    {
        this.Total_Leave=tl;
        this.Leave_6=l6;
        this.Leave_12=l12;
    }
}
