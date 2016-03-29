package fr.iutinfo.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Game level with his String content and the list of Instructions corresponding
 * @author Florent
 */
public class Level {

    private int id;
    private String name;
    private int authorId;
    private String content;
    private String levelType;
    private boolean orientation;
    private String instructions;
    private int maxInstructions;
    private List<Instruction> instructionsList;
    private LevelList levelList;

    public Level() {
        this(0);
    }

    public Level(int id) {
        this.id = id;
        instructionsList = new ArrayList<Instruction>();
    }

    private Integer[][] parseLevel(String content) {
        ArrayList<ArrayList<Integer>> structuredContent = new ArrayList<ArrayList<Integer>>();
        String[] lines = content.split(",");

        for (String line : lines) {
            String[] cells = line.split("\\s+");
            ArrayList<Integer> list = new ArrayList<Integer>();

            for (String cell : cells) {
                list.add(Integer.parseInt(cell));
            }

            structuredContent.add(list);
        }

        Integer[][] array = new Integer[structuredContent.size()][structuredContent.get(0).size()];

        for (int i = 0; i < structuredContent.size(); i++) {
            structuredContent.get(i).toArray(array[i]);
        }

        return array;
    }

    private String serializeContent(Integer[][] structuredContent) {
        String content = "";
        for (int i = 0; i < structuredContent.length; i++) {
            if (i != 0) {
                content += ",";
            }
            for (int j = 0; j < structuredContent[i].length; j++) {
                if (j != 0) {
                    content += " ";
                }
                content += structuredContent[i][j];
            }
        }
        return content;
    }

    private Integer[] parseInstructions(String instructions) {
        ArrayList<Integer> structuredInstructions = new ArrayList<Integer>();
        String[] cells = instructions.split(",");
        for (String cell : cells) {
            structuredInstructions.add(Integer.parseInt(cell));
        }
        Integer[] array = new Integer[structuredInstructions.size()];
        structuredInstructions.toArray(array);
        return array;
    }

    private String serializeInstructions(Integer[] structuredInstructions) {
        String instructions = "";
        for (int i = 0; i < structuredInstructions.length; i++) {
            if (i != 0) {
                instructions += ",";
            }
            instructions += structuredInstructions[i];
        }
        return instructions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStructuredContent(Integer[][] structuredContent) {
        content = serializeContent(structuredContent);
    }

    public Integer[][] getStructuredContent() {
        return parseLevel(content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getlevelType() {
        return levelType;
    }

    public void setlevelType(String type) {
        this.levelType = type;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int i) {
        this.authorId = i;
    }

    public String instructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer[] getStructuredInstructions() {
        return parseInstructions(instructions);
    }

    public void setStructuredInstructions(Integer[] structuredInstructions) {
        this.instructions = serializeInstructions(structuredInstructions);
    }

    public List<Instruction> getInstructionsList() {
        return instructionsList;
    }

    public void setInstructionsList(List<Instruction> list) {
        this.instructionsList = list;
    }

    public int getMaxInstructions() {
        return maxInstructions;
    }

    public void setMaxInstructions(int maxInstructions) {
        this.maxInstructions = maxInstructions;
    }

    public LevelList getLevelList() {
        return levelList;
    }

    public void setLevelList(LevelList levelList) {
        this.levelList = levelList;
    }

    /**
     * return true if the hero is oriented, false either way
     *
     * @return boolean
     */
    public boolean getOrientation() {
        return this.orientation;
    }

    /**
     * Setter for the orientation of the hero, true -> oriented, false ->
     * unoriented
     *
     * @param orientation
     */
    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }
}
