/*
 * Copyright (C) 2009  HungryHobo@mail.i2p
 * 
 * The GPG fingerprint for HungryHobo@mail.i2p is:
 * 6DD3 EAA2 9990 29BC 4AD2 7486 1E2C 7B61 76DC DC12
 * 
 * This file is part of I2P-Bote.
 * I2P-Bote is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * I2P-Bote is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with I2P-Bote.  If not, see <http://www.gnu.org/licenses/>.
 */

package i2p.bote.network;

import i2p.bote.packet.CommunicationPacket;
import net.i2p.data.Destination;

public class PacketBatchItem {
    private CommunicationPacket packet;
    private Destination destination;
    private boolean confirmed;
    
    PacketBatchItem(CommunicationPacket packet, Destination destination) {
        this.packet = packet;
        this.destination = destination;
        confirmed = false;
    }
    
    public CommunicationPacket getPacket() {
        return packet;
    }
    
    public Destination getDestination() {
        return destination;
    }

    void confirmDelivery() {
        confirmed = true;
    }
    
    boolean isDeliveryConfirmed() {
        return confirmed;
    }
}