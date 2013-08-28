package apcs.searchsort.search;

import java.util.List;

/**
 * A basic linear search implementation. Runs in O(<sup>n</sup>) time.
 */
public class LinearSearch implements SearchAlgorithm {
    
    @Override
    public <T> int indexOf(List<T> list, T target) {
        for (int i=0; i<list.size(); i++) {
            T item = list.get(i);
            if ((target == null && item == null)
             || (target.equals(list.get(i)))) {
                return i;
            }
        }
        return -1;
    }
    
}