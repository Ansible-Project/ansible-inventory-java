/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Andrea Scarpino <me@andreascarpino.it>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.andreascarpino.ansible.inventory.util;

import it.andreascarpino.ansible.inventory.type.Group;
import it.andreascarpino.ansible.inventory.type.Host;
import it.andreascarpino.ansible.inventory.type.Inventory;
import it.andreascarpino.ansible.inventory.type.Variable;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Andrea Scarpino
 */
public class InventoryWriter {

    private InventoryWriter() {
    }

    private static String groupHeader(String group) {
        return "[" + group + "]\n";
    }

    private static String variableBlock(Variable variable) {
        return " " + variable.getName() + "=" + variable.getValue();
    }

    private static String printHost(Host host) {
        final StringBuilder builder = new StringBuilder();
        builder.append(host.getName());

        for (Variable variable : host.getVariables()) {
            builder.append(variableBlock(variable));
        }

        builder.append("\n");

        return builder.toString();
    }

    private static void printHost(Host host, OutputStream stream) throws IOException {
        stream.write(host.getName().getBytes());

        for (Variable variable : host.getVariables()) {
            stream.write(variableBlock(variable).getBytes());
        }

        stream.write("\n".getBytes());
    }

    public static String write(Inventory inventory) {
        final StringBuilder builder = new StringBuilder();

        for (Host host : inventory.getHosts()) {
            builder.append(printHost(host));
        }

        for (Group group : inventory.getGroups()) {
            builder.append(groupHeader(group.getName()));

            for (Host host : group.getHosts()) {
                builder.append(printHost(host));
            }
        }

        return builder.toString();
    }

    public static void write(Inventory inventory, OutputStream stream) throws IOException {
        for (Host host : inventory.getHosts()) {
            printHost(host, stream);
        }

        for (Group group : inventory.getGroups()) {
            stream.write(groupHeader(group.getName()).getBytes());

            for (Host host : group.getHosts()) {
                printHost(host, stream);
            }

            stream.write("\n".getBytes());
        }
    }

}
