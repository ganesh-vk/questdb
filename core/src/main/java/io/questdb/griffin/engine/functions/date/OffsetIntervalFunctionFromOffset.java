/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2024 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.griffin.engine.functions.date;

import io.questdb.cairo.sql.Function;
import io.questdb.cairo.sql.Record;
import io.questdb.griffin.PlanSink;
import io.questdb.griffin.engine.functions.IntervalFunction;
import io.questdb.griffin.engine.functions.UnaryFunction;
import io.questdb.std.Interval;

class OffsetIntervalFunctionFromOffset extends IntervalFunction implements UnaryFunction {
    private final Function interval;
    private final long offset;

    public OffsetIntervalFunctionFromOffset(Function interval, long offset) {
        this.interval = interval;
        this.offset = offset;
    }

    @Override
    public Function getArg() {
        return interval;
    }

    @Override
    public Interval getInterval(Record rec) {
        return new Interval((interval.getInterval(rec).getLo() + offset), (interval.getInterval(rec).getHi() + offset));
    }

    @Override
    public void toPlan(PlanSink sink) {
        sink.val(interval).val('+').val(offset);
    }
}
