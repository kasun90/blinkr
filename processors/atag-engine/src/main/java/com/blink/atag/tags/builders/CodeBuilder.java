package com.blink.atag.tags.builders;

import com.blink.atag.tags.Code;
import com.blink.atag.tags.SimpleATag;
import com.blink.atag.tags.Text;

public class CodeBuilder extends SimpleATagBuilder {

    private Code code;

    @Override
    public void addLine(String line) {
        if (code == null)
            code = new Code();
        code.addCommand(new Text(line));
    }

    @Override
    public SimpleATag build() {
        return code;
    }

    @Override
    public boolean isBuilding() {
        return code != null && code.getCommandsCount() != 0;
    }

    @Override
    public void reset() {
        code = null;
    }
}
