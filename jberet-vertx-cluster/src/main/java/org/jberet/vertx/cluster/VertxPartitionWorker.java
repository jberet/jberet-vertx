/*
 * Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.vertx.cluster;

import java.io.Serializable;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import org.jberet.runtime.AbstractStepExecution;
import org.jberet.runtime.PartitionExecutionImpl;
import org.jberet.spi.PartitionInfo;
import org.jberet.spi.PartitionWorker;
import org.jberet.util.BatchUtil;
import org.jberet.vertx.cluster._private.VertxClusterLogger;

public class VertxPartitionWorker implements PartitionWorker {
    private EventBus eventBus;

    public VertxPartitionWorker(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void reportData(final Serializable data,
                           final AbstractStepExecution partitionExecution) throws Exception {
        final byte[] bytes = BatchUtil.objectToBytes(data);
        final long stepExecutionId = partitionExecution.getStepExecutionId();
        eventBus.send(PartitionInfo.getCollectorQueueName(stepExecutionId), Buffer.buffer(bytes));
        VertxClusterLogger.LOGGER.sendCollectorData(stepExecutionId,
                ((PartitionExecutionImpl) partitionExecution).getPartitionId(), data);
    }

    @Override
    public void partitionDone(final AbstractStepExecution partitionExecution) throws Exception {
        reportData(partitionExecution, partitionExecution);
    }

}
