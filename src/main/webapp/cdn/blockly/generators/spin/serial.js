/*
 This file contains support for serial connections in Spin

 Author: Vale Tolpegin ( valetolpegin@gmail.com )
 */

/*
 * Visual Blocks Language
 *
 * Copyright 2014 Vale Tolpegin.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict';

//define blocks
if (!Blockly.Blocks)
    Blockly.Blocks = {};


Blockly.Blocks.serial_open = {
    helpUrl: '',
    init: function () {
        this.setColour(colorPalette.getColor('protocols'));
        this.appendDummyInput("")
                .appendField("Serial init")
                .appendField("rxPIN#")
                .appendField(new Blockly.FieldDropdown(profile.default.digital), "RXPIN")
                .appendField("txPIN#")
                .appendField(new Blockly.FieldDropdown(profile.default.digital), "TXPIN");
        this.appendDummyInput("")
                .appendField("Baud")
                .appendField(new Blockly.FieldDropdown([["2400", "2400"], ["9600", "9600"], ["19200", "19200"], ["57600", "57600"], ["115200", "115200"]]), "BAUD");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
    }
};

Blockly.Blocks.serial_send_text = {
    helpUrl: '',
    init: function () {
        this.setColour(colorPalette.getColor('protocols'));
        this.appendDummyInput("")
                .appendField("Serial transmit")
                .appendField(this.newQuote_(true))
                .appendField(new Blockly.FieldTextInput(''), 'TEXT')
                .appendField(this.newQuote_(false));
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
    },
    /**
     * Create an image of an open or closed quote.
     * @param {boolean} open True if open quote, false if closed.
     * @return {!Blockly.FieldImage} The field image of the quote.
     * @this Blockly.Block
     * @private
     */
    newQuote_: function (open) {
        if (open == this.RTL) {
            var file = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAKCAQAAAAqJXdxAAAAqUlEQVQI1z3KvUpCcRiA8ef9E4JNHhI0aFEacm1o0BsI0Slx8wa8gLauoDnoBhq7DcfWhggONDmJJgqCPA7neJ7p934EOOKOnM8Q7PDElo/4x4lFb2DmuUjcUzS3URnGib9qaPNbuXvBO3sGPHJDRG6fGVdMSeWDP2q99FQdFrz26Gu5Tq7dFMzUvbXy8KXeAj57cOklgA+u1B5AoslLtGIHQMaCVnwDnADZIFIrXsoXrgAAAABJRU5ErkJggg==';
        } else {
            var file = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAKCAQAAAAqJXdxAAAAn0lEQVQI1z3OMa5BURSF4f/cQhAKjUQhuQmFNwGJEUi0RKN5rU7FHKhpjEH3TEMtkdBSCY1EIv8r7nFX9e29V7EBAOvu7RPjwmWGH/VuF8CyN9/OAdvqIXYLvtRaNjx9mMTDyo+NjAN1HNcl9ZQ5oQMM3dgDUqDo1l8DzvwmtZN7mnD+PkmLa+4mhrxVA9fRowBWmVBhFy5gYEjKMfz9AylsaRRgGzvZAAAAAElFTkSuQmCC';
        }
        return new Blockly.FieldImage(file, 12, 12, '"');
    }
};

Blockly.Blocks.serial_rx_byte = {
    helpUrl: '',
    init: function () {
        this.setColour(colorPalette.getColor('protocols'));
        this.appendDummyInput("")
                .appendField("Serial read byte");
        this.setOutput(true, 'Number');
//        this.setInputsInline(true);
    }
};

Blockly.Spin.serial_open = function () {
    var dropdown_rx_pin = this.getFieldValue('RXPIN');
    var dropdown_tx_pin = this.getFieldValue('TXPIN');
    var baud = this.getFieldValue('BAUD');

    Blockly.Spin.definitions_[ "include_serial" ] = 'OBJserial    : "Parallax Serial Terminal"';
    Blockly.Spin.serial_terminal_ = true;
    //  if (Blockly.Spin.setups_[ 'setup_serial' ] === undefined) {
    Blockly.Spin.setups_[ 'setup_serial' ] = 'serial.StartRxTx( ' + dropdown_rx_pin + ', ' + dropdown_tx_pin + ', %1100, ' + baud + ' )';
    // }

    return '';
};

Blockly.Spin.serial_send_text = function () {
    var text = this.getFieldValue('TEXT');

    Blockly.Spin.definitions_[ "include_serial" ] = 'OBJserial    : "Parallax Serial Terminal"';
    Blockly.Spin.serial_terminal_ = true;
    if (Blockly.Spin.setups_[ 'setup_serial' ] === undefined) {
        Blockly.Spin.setups_[ 'setup_serial' ] = 'serial.Start( ' + 115200 + ' )';
    }

    return 'serial.Str(String("' + text + '"))\n';
};

Blockly.Spin.serial_rx_byte = function () {

    Blockly.Spin.definitions_[ "include_serial" ] = 'OBJserial    : "Parallax Serial Terminal"';
    Blockly.Spin.serial_terminal_ = true;
    if (Blockly.Spin.setups_[ 'setup_serial' ] === undefined) {
        Blockly.Spin.setups_[ 'setup_serial' ] = 'serial.Start( ' + 115200 + ' )';
    }

    return ['serial.CharIn', Blockly.Spin.ORDER_ATOMIC];
};
