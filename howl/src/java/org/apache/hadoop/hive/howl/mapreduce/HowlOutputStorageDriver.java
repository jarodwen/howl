/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.hadoop.hive.howl.mapreduce;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.hive.metastore.api.Schema;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputFormat;


/** The abstract class to be implemented by underlying storage drivers to enable data access from Owl through
 *  OwlOutputFormat.
 */
public abstract class HowlOutputStorageDriver {
    /**
     * Returns the OutputFormat to use with this Storage Driver.
     * @param storeInfo the store info object containing parameters required for initialization of OutputFormat
     * @return the OutputFormat instance
     */
    public abstract OutputFormat<? super WritableComparable<?>, ? super Writable>
            getOutputFormat(StorerInfo storeInfo);

    /**
     * Set the data location for the output.
     * @param jobContext the job context object
     * @param location the data location
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public abstract void setOutputPath(JobContext jobContext, String location) throws IOException;

    /**
     * Set the schema for the data being written out.
     * @param jobContext the job context object
     * @param schema the data schema
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public abstract void setSchema(JobContext jobContext, Schema schema) throws IOException;

    /**
     * Sets the partition key values for the partition being written.
     * @param jobContext the job context object
     * @param partitionValues the partition values
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public abstract void setPartitionValues(JobContext jobContext, Map<String, String> partitionValues) throws IOException;

    /**
     * Convert the given WritableComparable key to the actual key type.
     * @param key the WritableComparable key to convert
     * @return a key instance
     */
    public abstract WritableComparable<?> convertKey(WritableComparable<?> key);

    /**
     * Convert the given HowlRecord value to the actual value type.
     * @param value the HowlRecord value to convert
     * @return a value instance
     */
    public abstract Writable convertValue(HowlRecord value);

    /**
     * Gets the location to use for the specified partition values. The default implementation returns null indicating
     * that the metadata service will generate the location. The storage driver can override as required.
     * @param jobContext the job context object
     * @param tableLocation the location of the table
     * @param partitionValues the partition values
     * @return the location String.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getPartitionLocation(JobContext jobContext,
            String tableLocation, Map<String, String> partitionValues) throws IOException { return null; };
}
