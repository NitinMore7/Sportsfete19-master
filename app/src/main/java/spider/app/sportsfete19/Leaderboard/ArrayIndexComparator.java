package spider.app.sportsfete19.Leaderboard;

import java.util.Comparator;
import java.util.List;

/*
 * Created by Srikanth Arugula on 9/1/18
 */


public class ArrayIndexComparator implements Comparator<Integer>
{
    private final List<String> array;

    public ArrayIndexComparator(List<String> array)
    {
        this.array = array;
    }

    public Integer[] createIndexArray()
    {
        Integer[] indexes = new Integer[array.size()];
        for (int i = 0; i < array.size(); i++)
        {
            indexes[i] = i; // Autoboxing
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2)
    {
        // Autounbox from Integer to int to use as array indexes
        return array.get(index1).compareTo(array.get(index2));
    }
}
