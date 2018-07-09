/*
 * Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.vertx.rest.sample;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import org.jberet.vertx.rest.JBeretRouterConfig;

/**
 * Test class for Vert.x-based JBeret REST API.
 *
 * @since 1.3.0.Beta7
 */
public class TestServer extends AbstractVerticle {
    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runExample(TestServer.class, null);
    }

    /**
     * Configures JBeret REST API routes by calling {@link JBeretRouterConfig#config(Router)} method,
     * and starts the test web server.
     */
    @Override
    public void start() {
        Router router = Router.router(vertx);
        JBeretRouterConfig.config(router);
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
