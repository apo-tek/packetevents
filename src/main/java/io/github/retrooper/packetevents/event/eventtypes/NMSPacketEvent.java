/*
 * MIT License
 *
 * Copyright (c) 2020 retrooper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.retrooper.packetevents.event.eventtypes;

import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.NMSPacket;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

/**
 * The {@code NMSPacketEvent} interface represents an event that has to do with an actual packet.
 * Don't mix this up with {@link io.github.retrooper.packetevents.event.PacketEvent}.
 * The PacketEvent class represents an event that belongs to PacketEvent's packet system.
 * @author retrooper
 * @since 1.8
 */
public interface NMSPacketEvent {
    /**
     * This method returns the name of the packet.
     * To get the name of the packet we get the class of the packet and then the name of the class.
     * See also: {@link Class#getSimpleName()}
     * We cache the name after the first call to improve performance.
     * Java 8 does not cache the name.
     * It is not recommended to call this method unless you NEED it.
     * If you are comparing packet types, use the {@link PacketType} byte system.
     * You would only need the packet name if packet type system doesn't contain your desired packet yet.
     * @return Name of the packet.
     */
    @Deprecated
    String getPacketName();

    /**
     * Get the NMS packet.
     * @return Get NMS packet.
     */
    NMSPacket getNMSPacket();

    /**
     * Update the NMS Packet.
     * @param packet NMS Object
     */
    void setNMSPacket(final NMSPacket packet);

    /**
     * Get the Packet ID.
     * @return Packet ID.
     */
    byte getPacketId();

    /**
     * Get the associated player's socket address.
     * @return Socket address of the associated player.
     */
    InetSocketAddress getSocketAddress();
}