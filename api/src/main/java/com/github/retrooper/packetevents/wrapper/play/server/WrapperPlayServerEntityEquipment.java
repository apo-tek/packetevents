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

package com.github.retrooper.packetevents.wrapper.play.server;

import com.github.retrooper.packetevents.event.impl.PacketSendEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class WrapperPlayServerEntityEquipment extends PacketWrapper<WrapperPlayServerEntityEquipment> {
    private int entityId;
    private List<Equipment> equipment;
    public WrapperPlayServerEntityEquipment(PacketSendEvent event) {
        super(event);
    }

    public WrapperPlayServerEntityEquipment(int entityId, List<Equipment> equipment) {
        super(PacketType.Play.Server.ENTITY_EQUIPMENT);
        this.entityId = entityId;
        this.equipment = equipment;
    }

    public WrapperPlayServerEntityEquipment(int entityId, Equipment... equipment) {
        this(entityId, Arrays.asList(equipment));
    }

    @Override
    public void readData() {
        if (serverVersion == ServerVersion.V_1_7_10) {
            entityId = readInt();
        }
        else {
            entityId = readVarInt();
        }
        equipment = new ArrayList<>();
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            byte value;
            do {
                value = readByte();
                EquipmentSlot equipmentSlot = EquipmentSlot.getById(value & Byte.MAX_VALUE);
                ItemStack itemStack = readItemStack();
                equipment.add(new Equipment(equipmentSlot, itemStack));
            } while ((value & Byte.MIN_VALUE) != 0);
        }
        else {
            EquipmentSlot slot;
            if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                slot = EquipmentSlot.getById(readVarInt());
            }
            else {
                slot = EquipmentSlot.getById(readShort());
            }
            equipment.add(new Equipment(slot, readItemStack()));
        }
    }

    @Override
    public void readData(WrapperPlayServerEntityEquipment wrapper) {
        entityId = wrapper.entityId;
        equipment = wrapper.equipment;
    }

    @Override
    public void writeData() {
        if (serverVersion == ServerVersion.V_1_7_10) {
            writeInt(entityId);
        }
        else {
            writeVarInt(entityId);
        }
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            for (int i = 0; i < this.equipment.size(); i++) {
                Equipment equipment = this.equipment.get(i);
                boolean last = i == (this.equipment.size() - 1);
                writeByte(last ? equipment.getSlot().getId() : (equipment.getSlot().getId() | Byte.MIN_VALUE));
                writeItemStack(equipment.getItem());
            }
        }
        else {
            Equipment equipment = this.equipment.get(0);
            if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                writeVarInt(equipment.getSlot().getId());
            }
            else {
                writeShort(equipment.getSlot().getId());
            }
            writeItemStack(equipment.getItem());
        }
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }


    public enum EquipmentSlot {
        MAINHAND,
        OFFHAND,
        BOOTS,
        LEGGINGS,
        CHESTPLATE,
        HELMET;

        public int getId() {
            return ordinal();
        }

        public static EquipmentSlot getById(int id) {
            return values()[id];
        }
    }

    public static class Equipment {
        private EquipmentSlot slot;
        private ItemStack item;

        public Equipment(EquipmentSlot slot, ItemStack item) {
            this.slot = slot;
            this.item = item;
        }

        public EquipmentSlot getSlot() {
            return slot;
        }

        public void setSlot(EquipmentSlot slot) {
            this.slot = slot;
        }

        public ItemStack getItem() {
            return item;
        }

        public void setItem(ItemStack item) {
            this.item = item;
        }
    }
}