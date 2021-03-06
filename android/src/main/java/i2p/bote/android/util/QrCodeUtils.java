/*
 * Copyright (C) 2014 str4d
 * Copyright (C) 2013-2014 Dominik Schürmann <dominik@dominikschuermann.de>
 * Copyright (C) 2011 Andreas Schildbach
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

package i2p.bote.android.util;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.i2p.I2PAppContext;
import net.i2p.util.Log;

import java.util.Hashtable;

/**
 * Copied from OpenKeychain
 * Originally copied from Bitcoin Wallet
 */
class QrCodeUtils {

    /**
     * Generate Bitmap with QR Code based on input.
     *
     * @param input The data to render as a QR code.
     * @return QR Code as Bitmap
     */
    static Bitmap getQRCodeBitmap(final String input) {
        try {
            final Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            final BitMatrix result = new QRCodeWriter().encode(input, BarcodeFormat.QR_CODE, 0,
                    0, hints);

            final int width = result.getWidth();
            final int height = result.getHeight();
            final int[] pixels = new int[width * height];

            for (int y = 0; y < height; y++) {
                final int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }

            final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (final WriterException e) {
            Log log = I2PAppContext.getGlobalContext().logManager().getLog(QrCodeUtils.class);
            if (log.shouldLog(Log.ERROR))
                log.error("QrCodeUtils", e);
            return null;
        }
    }
}
