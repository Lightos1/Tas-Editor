# A work-in-progress tas editor using sys-botbase

### Requirements:
 - As of right now, only windows is guaranteed to work properly
   - Linux support will follow probably in the future
 - JDK23
 - [Sys-botbase](https://github.com/olliz0r/sys-botbase) installed
   - the most recent version of [atmosphere](https://github.com/Atmosphere-NX/Atmosphere/releases) is required for sys-botbase to function
   - on boot, if sys-botbase is enabled, the LED on the controller should light up blue
     - if not, update atmosphere and ensure sys-botbase is being started on boot

### Usage
#### Configuration:
Under TAS > settings, you can configure:
1. The IP address (has to match the IP address of your switch)
2. The port (6000 by default)
3. The length of a button press and delay between each input
   - This will potentially differ in each game, so testing is required
   - for instance, 28 works well for botw
4. Various options to invert the analog sticks

Make sure to press ``Save Settings``.<br>
The settings are stored inside ``~/AppData/Local/Tas_Editor/tas_editor.conf``.<br>

The usage is very straight forward, you can enter the inputs like with any tas editor.<br>
Pressing ``enter`` will add a new row, pressing ``delete`` deletes a row.<br>
To enter stick inputs, open the stick input window in TAS > Stick inputs. Changing the stick will change the values in the last row that was interacted with.<br>

First, you have to connect: Tas > connect, you may have to run it twice<br>
To disconnect, simply press disconnect, you should run this every time you want to regain control or close the editor.
To run it, you have to save it first, either press ``W`` or File > Save. Any file name or extension will work.

To actually run it, either press ``R`` or Tas > Play.
 - If it doesn't run, ensure you saved the inputs, sys-botbase is running, you are connected, using the ip correct address and port.
 - If you are using ``R``, make sure to highlight/click on the actual input table or it won't work, this is a bug, sorry :)
 - If you are still having issues, press disconnect a few times.
   - You may have to spam it in case it is being stubborn.
 - If nothing works or it isn't disconnecting, reboot the console.
 - Note: ``W`` and ``R`` will potentially get changed in the future to other keyboard shortcuts.

Opening files is also straight forward: Files > Open, there is no keyboard shortcut yet.<br>
Note that there may be bugs such as input loss (sorry) with opening files, please report them.🙏

With an almost 100% certainty, there are bugs in the editor left.

## Credits:
- [olliz0r](https://github.com/olliz0r): [sys-botbase](https://github.com/olliz0r/sys-botbase)
