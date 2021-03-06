package teamasm.moh.reference;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourARGB;
import codechicken.lib.util.ItemNBTUtils;
import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandom;
import teamasm.moh.init.ModItems;
import teamasm.moh.util.EnumFinalProduct;

import java.util.*;

/**
 * Created by brandon3055 on 6/08/2016.
 * This will be the registry that handles ALL the ores.
 */
public class OreRegistry {

    public static final OreRegistry INSTANCE = new OreRegistry();

    private List<String> oreList = new LinkedList<String>();
    public List<WeightedOre> weightedOres = new ArrayList<WeightedOre>();
    private static List<WeightedChance> weightedChance = new ArrayList<WeightedChance>();
    private Map<String, Integer> nameToGenWeight = new HashMap<String, Integer>();
    private Map<String, Colour> nameToColour = new HashMap<String, Colour>();
    private Map<String, Float> nameToMaxPurity = new HashMap<String, Float>();
    private Map<String, Float> nameToMinPurity = new HashMap<String, Float>();
    private Map<String, EnumFinalProduct> nameToProduct = new HashMap<String, EnumFinalProduct>();
    //todo Add getters as needed

    /**
     * The maximum possible ores per vein.
     */
    private static int maxOresPerOre = 5;

    static {
        for (int i = 1; i <= maxOresPerOre; i++) {
            int chance = maxOresPerOre - i;
            weightedChance.add(new WeightedChance(i, 1 + (chance * 2)));
        }
    }

    /**
     * @param oreName   The ore dictionary name of the ore.
     * @param genWeight The generation weight of the ore. Higher numbers have a higher chance to generate.
     * @param maxPurity The maximum purity this ore can spawn with. range 0 > 1. Should always be greater than 0. Purity of 1 = 4 ingots ber block.
     * @param oreColour The colour for the ore item.
     */
    public void registerOre(String oreName, int genWeight, float minPurity, float maxPurity, int oreColour, EnumFinalProduct product) {
        oreList.add(oreName);
        nameToGenWeight.put(oreName, genWeight);
        nameToColour.put(oreName, new ColourARGB(oreColour));
        weightedOres.add(new WeightedOre(oreName, genWeight));
        nameToMaxPurity.put(oreName, maxPurity);
        nameToMinPurity.put(oreName, minPurity);
        nameToProduct.put(oreName, product);
    }

    public List<String> getOreList() {
        return oreList;
    }

    public ImmutableMap<String, Colour> getOreColourMap(){
        return ImmutableMap.copyOf(nameToColour);
    }

    public boolean isOreRegistered(String ore) {
        return oreList.contains(ore);
    }

    public ItemStack getRandomOreForSeed(Random random) {
        int oreCount = WeightedRandom.getRandomItem(random, weightedChance).count;
        ItemStack stack = new ItemStack(ModItems.brokenOre);
        Map<String, Float> ores = new HashMap<String, Float>();

        for (int i = 0; i < oreCount; i++) {
            WeightedOre ore = WeightedRandom.getRandomItem(random, weightedOres);
            String name = ore.name;

            float min = nameToMinPurity.get(name);
            float purity = min + (random.nextFloat() * (nameToMaxPurity.get(name) - min));

            if (ores.containsKey(name)) {
                ores.put(name, ores.get(name) + purity);
            } else {
                ores.put(name, purity);
            }
        }

        NBTTagList oreList = new NBTTagList();
        for (String name : ores.keySet()) {
            NBTTagCompound oreCompound = new NBTTagCompound();
            oreCompound.setString("Name", name);
            oreCompound.setFloat("Purity", ores.get(name));
            oreList.appendTag(oreCompound);
        }

        NBTTagCompound itemCompound = ItemNBTUtils.validateTagExists(stack);
        itemCompound.setTag("Ores", oreList);

        return stack;
    }

    public static class WeightedOre extends WeightedRandom.Item {

        public final String name;

        public WeightedOre(String name, int itemWeightIn) {
            super(itemWeightIn);
            this.name = name;
        }
    }

    private static class WeightedChance extends WeightedRandom.Item {

        private final int count;

        public WeightedChance(int count, int itemWeightIn) {
            super(itemWeightIn);
            this.count = count;
        }
    }

    /**
     * Returns a map of ores contained within the given stack and their purity.
     *
     * @param stack the stack.
     * @return a map or ore name to purity.
     */
    public static Map<String, Float> getOres(ItemStack stack) {
        Map<String, Float> oreMap = new HashMap<String, Float>();
        NBTTagCompound compound = stack.getTagCompound();

        if (compound == null || !compound.hasKey("Ores")) {

            return oreMap;
        }

        NBTTagList oreList = compound.getTagList("Ores", 10);
        for (int i = 0; i < oreList.tagCount(); i++) {
            oreMap.put(oreList.getCompoundTagAt(i).getString("Name"), oreList.getCompoundTagAt(i).getFloat("Purity"));
        }

        return oreMap;
    }

    public static int getOreIndex(String name) {
        return INSTANCE.getOreList().indexOf(name);
    }

    public static String getFromIndex(int index) {
        return index < 0 || index >= INSTANCE.getOreList().size() ? null : INSTANCE.getOreList().get(index);
    }

    public static String getProduct(String name) {
        if (name == null || !INSTANCE.nameToProduct.containsKey(name)) {
            return null;
        }

        return name.replace("ore", INSTANCE.nameToProduct.get(name).name().toLowerCase());
    }
}
