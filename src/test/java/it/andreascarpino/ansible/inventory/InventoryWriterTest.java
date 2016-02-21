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
package it.andreascarpino.ansible.inventory;

import it.andreascarpino.ansible.inventory.type.Group;
import it.andreascarpino.ansible.inventory.type.Host;
import it.andreascarpino.ansible.inventory.type.Inventory;
import it.andreascarpino.ansible.inventory.type.Variable;
import it.andreascarpino.ansible.inventory.util.InventoryWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Andrea Scarpino
 */
public class InventoryWriterTest {

    @Test
    public void testWriteSimple() {
        Inventory inventory = new Inventory();

        Group group = new Group("group1");
        inventory.addGroup(group);

        Host host = new Host("host1");
        group.addHost(host);

        Variable var = new Variable("var1", "value1");
        host.addVariable(var);

        String inventoryText = InventoryWriter.write(inventory);

        Assert.assertEquals("[group1]\nhost1 var1=value1\n", inventoryText);

        inventory = new Inventory();

        inventory.addHost(host);

        inventoryText = InventoryWriter.write(inventory);

        Assert.assertEquals("host1 var1=value1\n", inventoryText);
    }

}
