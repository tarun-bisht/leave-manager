/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package date_class;
/**
 *
 * @author Tarun Bisht
 */
public class MyDate
{
    int day,month,year;
    int[] monthDays={31,28,31,30,31,30,31,31,30,31,30,31};
    public MyDate(int day,int month,int year)
    {
        this.day=day;
        this.month=month;
        this.year=year;
    }
    public MyDate(String date,String seperator)
    {
        String[] dateString =date.split(seperator);
        this.day=Integer.parseInt(dateString[2]);
        this.month=Integer.parseInt(dateString[1]);
        this.year=Integer.parseInt(dateString[0]);
    }
    public int Year()
    {
        return this.year;
    }
    public int Month()
    {
        return this.month;
    }
    public int Day()
    {
        return this.day;
    }
    private int FindMagicNumber(MyDate end)
    {
        if(this.month==end.month)
        {
            return end.day - this.day;
        }
        else if(this.month>end.month)
        {
            if(CheckLeapYear(end.year))
            {
                this.monthDays[1]=29;
            }
            int a,b,number=0;
            a=end.month;
            b=this.month;
            while(a<(b-1))
            {
                number+=this.monthDays[a];
                a+=1;
            }
            number+=this.monthDays[end.month-1]-end.day;
            number+=this.day;
            return number;
        }
        else if(this.month<end.month)
        {
            if(CheckLeapYear(this.year))
            {
                this.monthDays[1]=29;
            }
            int a,b,number=0;
            a=this.month;
            b=end.month;
            while(a<(b-1))
            {
                number+=this.monthDays[a];
                a+=1;
            }
            number+=this.monthDays[this.month-1]-this.day;
            number+=end.day;
            return number;
        }
        return -1;
    }
    private int TotalLeapYear(int start,int end)
    {
        float diff=end-start;
        if (diff<0)
                return 0;
        else if(diff==0)
        {
            if (CheckLeapYear(start) && CheckLeapYear(end))
                return 1;
            else
                return 0;
        }
        else if(diff==1)
        {
            if (CheckLeapYear(start) || CheckLeapYear(end))
                return 1;
            else
                return 0;
        }
        int leap= roundoff(diff/4);
        int i=HundredYear(start);
        while (i<=end)
        {
            if (!CheckLeapYear(i))
                leap-=1;
            i+=100;
        }   
        if (CheckLeapYear(start) && CheckLeapYear(end))
            leap+=1;
        System.out.println("leap factor: "+leap);
        return leap;
    }
    private int HundredYear(int year)
    {
        return (int) (Math.floor((year+99)/100)*100);
    }
    private int roundoff(double number)
    {
        double diff= number-Math.floor(number);
        if(diff<0.5)
            return (int) Math.floor(number);
        return (int) Math.ceil(number);
    }  
    private boolean ValidateDate(MyDate end)
    {
        return end.year>=this.year;
    }
    public boolean CheckLeapYear(int year)
    {
        return (year%4==0 && year%100!=0) || year%400==0;
    }
    public int DaysBetweenDate(MyDate toDate)
    {
        if(ValidateDate(toDate))
        {
            int diff,leapFactor,magic;
            diff=toDate.year-this.year;
            leapFactor=TotalLeapYear(this.year,toDate.year);
            magic= FindMagicNumber(toDate);
            if(toDate.year == this.year)
            {
                return magic+leapFactor;
            }
            else if(toDate.month < this.month)
            {
                return (diff*365)+leapFactor-magic;
            }
            else if(toDate.month >= this.month)
            {
                return (diff*365)+leapFactor+magic;
            }
        }
        return -1;
    }
    public int DaysBetweenDate(MyDate toDate,boolean include_startday)
    {
        if(ValidateDate(toDate))
        {
            int diff,leapFactor,magic;
            diff=toDate.year-this.year;
            leapFactor=TotalLeapYear(this.year,toDate.year);
            magic= FindMagicNumber(toDate);
            if(include_startday)
                magic+=1;
            if(toDate.year == this.year)
            {
                return magic+leapFactor;
            }
            else if(toDate.month < this.month)
            {
                return (diff*365)+leapFactor-magic;
            }
            else if(toDate.month >= this.month)
            {
                return (diff*365)+leapFactor+magic;
            }
        }
        return -1;
    }
    public boolean isGreaterMonth(int monthindex)
    {
        return monthindex<this.month;
    }
    public boolean isGreaterDateThan(MyDate date)
    {
        if(this.year>date.year)
            return true;
        else
        {
            if(this.year==date.year && this.month>date.month)
                return true;
            else
            {
                if(this.year==date.year && this.month == date.month && this.day>date.day)
                    return true;
            }
        }
        return false;
    }
    public boolean isValidDate()
    {
        if(this.month<13 || this.month>0)
            return true;
        if(this.year>0)
            return true;
        if(this.day<32)
        {
            if(this.day==monthDays[this.month])
                return true;
        }
        return false;
    }
}