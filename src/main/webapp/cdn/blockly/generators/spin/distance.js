/*
 This file contains support for distance sensors

 Author: Vale Tolpegin ( valetolpegin@gmail.com )

 *Copyright 2014 Vale Tolpegin.
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

//Create GUI blocks for SF02 Laser Rangefinder
Blockly.Blocks.SF02_Laser_Rangefinder = {
    helpUrl: '',
    init: function () {
        this.setColour(colorPalette.getColor('input'));
        this.appendDummyInput("")
                .appendField("SF02 Laser Rangefinder Pin")
                .appendField(new Blockly.FieldDropdown(profile.default.digital), "PIN");
        this.setOutput(true, Number);
    }
};

//Get generator
//Blockly.Spin = new Blockly.Generator('Spin');

//Create code for blocks
Blockly.Spin.SF02_Laser_Rangefinder = function () {
    var pin = this.getFieldValue('PIN');

    //ADD SPIN CODE
    Blockly.Spin.definitions_[ "include_serial" ] = 'serial : "Parallax Serial Terminal"';
    if (Blockly.Spin.setups_[ "Laser_Rangefinder" ] === undefined) {
        Blockly.Spin.setups_[ "Laser_Rangefinder" ] = 'serial.StartRxTx( ' + pin + ', ' + pin + ', %1100, 9200 );';
    }

    var code = 'serial.CharIn( ' + pin + ' )';
    return code;
};
