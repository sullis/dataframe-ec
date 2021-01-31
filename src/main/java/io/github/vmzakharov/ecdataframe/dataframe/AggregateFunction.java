package io.github.vmzakharov.ecdataframe.dataframe;

import org.eclipse.collections.api.DoubleIterable;
import org.eclipse.collections.api.LongIterable;

public abstract class AggregateFunction
{
    private final String columnName;
    private final String targetColumnName;

    public static AggregateFunction sum(String newColumnName)
    {
        return new Sum(newColumnName);
    }

    public static AggregateFunction sum(String newColumnName, String newTargetColumnName)
    {
        return new Sum(newColumnName, newTargetColumnName);
    }

    public static AggregateFunction min(String newColumnName)
    {
        return new Min(newColumnName);
    }

    public static AggregateFunction min(String newColumnName, String newTargetColumnName)
    {
        return new Min(newColumnName, newTargetColumnName);
    }

    public static AggregateFunction max(String newColumnName)
    {
        return new Max(newColumnName);
    }

    public static AggregateFunction max(String newColumnName, String newTargetColumnName)
    {
        return new Max(newColumnName, newTargetColumnName);
    }

    public static AggregateFunction avg(String newColumnName)
    {
        return new Avg(newColumnName);
    }

    public static AggregateFunction avg(String newColumnName, String newTargetColumnName)
    {
        return new Avg(newColumnName, newTargetColumnName);
    }

    public AggregateFunction(String newColumnName)
    {
        this(newColumnName, newColumnName);
    }

    public AggregateFunction(String newColumnName, String newTargetColumnName)
    {
        this.columnName = newColumnName;
        this.targetColumnName = newTargetColumnName;
    }

    public String getColumnName()
    {
        return this.columnName;
    }

    public String getTargetColumnName()
    {
        return this.targetColumnName;
    }

    abstract double applyDoubleIterable(DoubleIterable items);
    abstract long applyLongIterable(LongIterable items);

    abstract long longInitialValue();
    abstract double doubleInitialValue();

    abstract long longAccumulator(long currentAggregate, long newValue);
    abstract double doubleAccumulator(double currentAggregate, double newValue);

    abstract public String getDescription();

    public long defaultLongIfEmpty()
    {
        throw new UnsupportedOperationException("Operation " + this.getDescription() + " is not defined on empty lists");
    }

    public double defaultDoubleIfEmpty()
    {
        throw new UnsupportedOperationException("Operation " + this.getDescription() + " is not defined on empty lists");
    }

    public static class Sum
    extends AggregateFunction
    {
        public Sum(String newColumnName)
        {
            super(newColumnName);
        }

        public Sum(String newColumnName, String newTargetColumnName)
        {
            super(newColumnName, newTargetColumnName);
        }

        @Override
        public String getDescription()
        {
            return "SUM";
        }

        @Override
        public double applyDoubleIterable(DoubleIterable items)
        {
            return items.sum();
        }

        @Override
        public long applyLongIterable(LongIterable items)
        {
            return items.sum();
        }

        @Override
        long longInitialValue()
        {
            return 0L;
        }

        @Override
        double doubleInitialValue()
        {
            return 0.0;
        }

        @Override
        long longAccumulator(long currentAggregate, long newValue)
        {
            return currentAggregate + newValue;
        }

        @Override
        double doubleAccumulator(double currentAggregate, double newValue)
        {
            return currentAggregate + newValue;
        }

        @Override
        public long defaultLongIfEmpty()
        {
            return 0L;
        }

        @Override
        public double defaultDoubleIfEmpty()
        {
            return 0.0;
        }
    };

    public static class Max
    extends AggregateFunction
    {
        public Max(String newColumnName)
        {
            super(newColumnName);
        }

        public Max(String newColumnName, String newTargetColumnName)
        {
            super(newColumnName, newTargetColumnName);
        }

        @Override
        public String getDescription()
        {
            return "MAX";
        }

        @Override
        public double applyDoubleIterable(DoubleIterable items)
        {
            return items.max();
        }

        @Override
        public long applyLongIterable(LongIterable items)
        {
            return items.max();
        }

        @Override
        long longInitialValue()
        {
            return Long.MIN_VALUE;
        }

        @Override
        double doubleInitialValue()
        {
            return Double.MIN_VALUE;
        }

        @Override
        long longAccumulator(long currentAggregate, long newValue)
        {
            return Math.max(currentAggregate, newValue);
        }

        @Override
        double doubleAccumulator(double currentAggregate, double newValue)
        {
            return Math.max(currentAggregate, newValue);
        }
    };

    public static class Min
    extends AggregateFunction
    {
        public Min(String newColumnName)
        {
            super(newColumnName);
        }

        public Min(String newColumnName, String newTargetColumnName)
        {
            super(newColumnName, newTargetColumnName);
        }

        @Override
        public String getDescription()
        {
            return "MIN";
        }

        @Override
        public double applyDoubleIterable(DoubleIterable items)
        {
            return items.min();
        }

        @Override
        public long applyLongIterable(LongIterable items)
        {
            return items.min();
        }

        @Override
        long longInitialValue()
        {
            return Long.MAX_VALUE;
        }

        @Override
        double doubleInitialValue()
        {
            return Double.MAX_VALUE;
        }

        @Override
        long longAccumulator(long currentAggregate, long newValue)
        {
            return Math.min(currentAggregate, newValue);
        }

        @Override
        double doubleAccumulator(double currentAggregate, double newValue)
        {
            return Math.min(currentAggregate, newValue);
        }
    };

    public static class Avg
    extends AggregateFunction
    {
        public Avg(String newColumnName)
        {
            super(newColumnName);
        }

        public Avg(String newColumnName, String newTargetColumnName)
        {
            super(newColumnName, newTargetColumnName);
        }

        @Override
        public String getDescription()
        {
            return "AVG";
        }

        @Override
        public double applyDoubleIterable(DoubleIterable items)
        {
            return items.average();
        }

        @Override
        public long applyLongIterable(LongIterable items)
        {
            return Math.round(items.average());
        }

        @Override
        long longAccumulator(long currentAggregate, long newValue)
        {
            return currentAggregate + newValue;
        }

        @Override
        double doubleAccumulator(double currentAggregate, double newValue)
        {
            return currentAggregate + newValue;
        }

        @Override
        long longInitialValue()
        {
            return 0;
        }

        @Override
        double doubleInitialValue()
        {
            return 0;
        }

        @Override
        public long defaultLongIfEmpty()
        {
            return 0L;
        }

        @Override
        public double defaultDoubleIfEmpty()
        {
            return 0.0;
        }
    };
}

