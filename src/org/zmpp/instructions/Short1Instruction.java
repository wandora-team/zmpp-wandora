/*
 * $Id: Short1Instruction.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 10/03/2005
 * Copyright 2005-2008 by Wei-ju Wu
 * This file is part of The Z-machine Preservation Project (ZMPP).
 *
 * ZMPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ZMPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ZMPP.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.zmpp.instructions;

import org.zmpp.vm.Machine;

/**
 * This class represents instructions of type SHORT, 1OP.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class Short1Instruction extends AbstractInstruction {

    /**
     * Constructor.
     *
     * @param machineState a reference to the MachineState object
     * @param opcode the instruction's opcode
     */
    public Short1Instruction(Machine machineState, int opcode) {

        super(machineState, opcode);
    }

    /**
     * {@inheritDoc}
     */
    protected void doInstruction() {

        switch (getOpcode()) {
            case Short1StaticInfo.OP_JZ:
                jz();
                break;
            case Short1StaticInfo.OP_GET_SIBLING:
                get_sibling();
                break;
            case Short1StaticInfo.OP_GET_CHILD:
                get_child();
                break;
            case Short1StaticInfo.OP_GET_PARENT:
                get_parent();
                break;
            case Short1StaticInfo.OP_GET_PROP_LEN:
                get_prop_len();
                break;
            case Short1StaticInfo.OP_INC:
                inc();
                break;
            case Short1StaticInfo.OP_DEC:
                dec();
                break;
            case Short1StaticInfo.OP_PRINT_ADDR:
                print_addr();
                break;
            case Short1StaticInfo.OP_REMOVE_OBJ:
                remove_obj();
                break;
            case Short1StaticInfo.OP_PRINT_OBJ:
                print_obj();
                break;
            case Short1StaticInfo.OP_JUMP:
                jump();
                break;
            case Short1StaticInfo.OP_RET:
                ret();
                break;
            case Short1StaticInfo.OP_PRINT_PADDR:
                print_paddr();
                break;
            case Short1StaticInfo.OP_LOAD:
                load();
                break;
            case Short1StaticInfo.OP_NOT:
                if (getStoryFileVersion() <= 4) {

                    not();

                } else {

                    call_1n();
                }
                break;
            case Short1StaticInfo.OP_CALL_1S:
                call_1s();
                break;
            default:
                throwInvalidOpcode();
        }
    }

    /**
     * {@inheritDoc}
     */
    public InstructionForm getInstructionForm() {

        return InstructionForm.SHORT;
    }

    /**
     * {@inheritDoc}
     */
    public OperandCount getOperandCount() {

        return OperandCount.C1OP;
    }

    /**
     * {@inheritDoc}
     */
    protected InstructionStaticInfo getStaticInfo() {

        return Short1StaticInfo.getInstance();
    }

    /**
     * inc instruction.
     */
    private void inc() {

        final short varNum = getValue(0);
        final short value = getCpu().getVariable(varNum);
        getCpu().setVariable(varNum, (short) (value + 1));
        nextInstruction();
    }

    /**
     * dec instruction.
     */
    private void dec() {

        final short varNum = getValue(0);
        final short value = (short) getCpu().getVariable(varNum);
        getCpu().setVariable(varNum, (short) (value - 1));
        nextInstruction();
    }

    /**
     * not instruction.
     */
    private void not() {

        final int notvalue = ~getUnsignedValue(0);
        storeResult((short) (notvalue & 0xffff));
        nextInstruction();
    }

    /**
     * jump instruction. The offset can be negative.
     */
    private void jump() {

        // Unconditional jump
        getCpu().incrementProgramCounter(getValue(0) + 1);
    }

    /**
     * load instruction.
     */
    private void load() {

        final int varnum = getValue(0);
        final short value = varnum == 0 ? getCpu().getStackTopElement()
                : getCpu().getVariable(varnum);
        storeResult(value);
        nextInstruction();
    }

    /**
     * jz instruction.
     */
    private void jz() {

        branchOnTest(getValue(0) == 0);
    }

    /**
     * get_parent instruction.
     */
    private void get_parent() {
        final int obj = getUnsignedValue(0);
        int parent = 0;
        if (obj > 0) {
            parent = getMachine().getParent(obj);
        } else {
            getMachine().warn("@get_parent illegal access to object " + obj);
        }
        storeResult((short) (parent & 0xffff));
        nextInstruction();
    }

    private void get_sibling() {
        final int obj = getUnsignedValue(0);
        int sibling = 0;
        if (obj > 0) {
            sibling = getMachine().getSibling(obj);
        } else {
            getMachine().warn("@get_sibling illegal access to object " + obj);
        }
        storeResult((short) (sibling & 0xffff));
        branchOnTest(sibling > 0);
    }

    private void get_child() {
        final int obj = getUnsignedValue(0);
        int child = 0;
        if (obj > 0) {
            child = getMachine().getChild(obj);
        } else {
            getMachine().warn("@get_child illegal access to object " + obj);
        }
        storeResult((short) (child & 0xffff));
        branchOnTest(child > 0);
    }

    private void print_addr() {
        getMachine().getOutput().printZString(getUnsignedValue(0));
        nextInstruction();
    }

    private void print_paddr() {
        getMachine().getOutput().printZString(
                getMachine().getCpu().translatePackedAddress(getUnsignedValue(0), false));
        nextInstruction();
    }

    private void ret() {
        returnFromRoutine(getValue(0));
    }

    private void print_obj() {
        final int obj = getUnsignedValue(0);
        if (obj > 0) {
            getMachine().getOutput().printZString(
                    getMachine().getPropertiesDescriptionAddress(obj));
        } else {
            getMachine().warn("@print_obj illegal access to object " + obj);
        }
        nextInstruction();
    }

    private void remove_obj() {
        final int obj = getUnsignedValue(0);
        if (obj > 0) {
            getMachine().removeObject(obj);
        }
        nextInstruction();
    }

    private void get_prop_len() {
        final int propertyAddress = getUnsignedValue(0);
        final short proplen = (short) getMachine().getPropertyLength(propertyAddress);
        storeResult(proplen);
        nextInstruction();
    }

    private void call_1s() {
        call(0);
    }

    private void call_1n() {
        call(0);
    }
}
