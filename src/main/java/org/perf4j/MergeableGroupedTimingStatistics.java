package org.perf4j;

import java.util.SortedMap;

/**
 * @author WuCY
 */
public class MergeableGroupedTimingStatistics extends GroupedTimingStatistics implements Combiner<GroupedTimingStatistics> {
    /**
     * Merge another GroupedTimingStatistics object and returns a new one
     *
     * @param other The object to merge-in.
     * @return the new GroupedTimingStatistics object
     */
    public MergeableGroupedTimingStatistics merge(GroupedTimingStatistics other) {
        MergeableGroupedTimingStatistics result = new MergeableGroupedTimingStatistics();

        result.setStartTime(this.getStartTime());
        if (other.getStartTime() < result.getStartTime()) result.setStartTime(other.getStartTime());
        result.setStopTime(this.getStopTime());
        if (other.getStopTime() > result.getStopTime()) result.setStopTime(other.getStopTime());
        result.setCreateRollupStatistics(this.isCreateRollupStatistics() | other.isCreateRollupStatistics());

        SortedMap<String,TimingStatistics> map = result.getStatisticsByTag();
        for (String tag: other.getStatisticsByTag().keySet()) {
            if (this.getStatisticsByTag().containsKey(tag))
                map.put(tag, this.getStatisticsByTag().get(tag).merge(other.getStatisticsByTag().get(tag)));
            else
                map.put(tag, other.getStatisticsByTag().get(tag));
        }
        for (String tag: this.getStatisticsByTag().keySet())
            if (!other.getStatisticsByTag().containsKey(tag))
                map.put(tag, this.getStatisticsByTag().get(tag));
        result.setStatisticsByTag(map);
        return result;
    }

    public String toString() {
        return "Total " + super.toString();
    }
}
