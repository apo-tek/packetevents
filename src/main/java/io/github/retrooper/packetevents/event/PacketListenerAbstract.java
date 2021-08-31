/*
 * This file is part of packetevents - https://github.com/retrooper/packetevents
 * Copyright (C) 2021 retrooper and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.retrooper.packetevents.event;

import io.github.retrooper.packetevents.event.impl.*;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Abstract packet listener.
 *
 * @author retrooper
 * @since 1.8
 */
public abstract class PacketListenerAbstract {
    private final PacketListenerPriority priority;
    protected final Map<Byte, List<Method>> methods;

    public PacketListenerAbstract(PacketListenerPriority priority) {
        this.priority = priority;
        this.methods = null;
    }

    public PacketListenerAbstract(PacketListenerPriority priority, Map<Byte, List<Method>> methods) {
        this.priority = priority;
        this.methods = methods;
    }

    public PacketListenerAbstract() {
        this.priority = PacketListenerPriority.NORMAL;
        this.methods = null;
    }

    public PacketListenerPriority getPriority() {
        return priority;
    }

    public void onPlayerInject(PlayerInjectEvent event) {}

    public void onPostPlayerInject(PostPlayerInjectEvent event) {}

    public void onPlayerEject(PlayerEjectEvent event) {}

    public void onPacketReceive(PacketReceiveEvent event) {}

    public void onPacketSend(PacketSendEvent event) {}

    public void onPacketEventExternal(PacketEvent event) {}

}
