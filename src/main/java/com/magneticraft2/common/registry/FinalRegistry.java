package com.magneticraft2.common.registry;

import com.magneticraft2.common.block.BlockMagneticraft2Pattern;
import com.magneticraft2.common.block.crops.RicePlantBlock;
import com.magneticraft2.common.block.machines.heat.CrucibleHeaterBlock;
import com.magneticraft2.common.block.machines.heat.HeatGeneratorBlock;
import com.magneticraft2.common.block.machines.multiblocks.hgenerator.hgeneratormultiblocktest;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.systems.multiblockpattern.MultiblockPatterns;
import com.magneticraft2.common.systems.multiblockpattern.block.AirLockBlock;
import com.magneticraft2.common.systems.multiblockpattern.block.CrucibleMBBlock;
import com.magneticraft2.common.systems.multiblockpattern.block.MultiblockBlock;
import com.magneticraft2.common.systems.multiblockpattern.item.MultiblockBlockItem;
import com.magneticraft2.common.systems.multiblockpattern.tile.MultiblockMasterTile;
import com.magneticraft2.common.systems.multiblockpattern.tile.MultiblockTile;
import com.magneticraft2.common.systems.multiblockpattern.tile.crucibleBlocktile;
import com.magneticraft2.common.tile.machines.heat.CrucibleHeaterTile;
import com.magneticraft2.common.tile.machines.heat.HeatGeneratorTile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static com.magneticraft2.common.magneticraft2.MOD_ID;

public class FinalRegistry {
    public static List<Item> BLOCK_ITEMS_LIST = new ArrayList<Item>();

    private static final Logger LOGGER = LogManager.getLogger();
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    private static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);


    public static void register(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILE_ENTITIES.register(bus);
        ENTITIES.register(bus);
        STRUCTURES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        ContainerAndScreenRegistry.containerRegistry();
    }


    public static final CreativeModeTab MC2Blocks = new CreativeModeTab("Magneticraft2.Blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(cruciblemb_block.get());
        }
    };
    public static final CreativeModeTab MC2Machines = new CreativeModeTab("Magneticraft2.Machines") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Block_Heat_Generator.get());
        }
    };
    public static final CreativeModeTab MC2Plants = new CreativeModeTab("Magneticraft2.Plants") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(rice_seed.get());
        }
    };
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(MC2Blocks);

    public static final RegistryObject<Block> rice_plant = registerBlockWithoutBlockItem("rice_plant", () -> new RicePlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
    public static final RegistryObject<Item> rice_seed = ITEMS.register("rice_seed", () -> new ItemNameBlockItem(rice_plant.get(), new Item.Properties().tab(MC2Plants)));
    public static final RegistryObject<Item> rice = ITEMS.register("rice", () -> new Item(new Item.Properties().tab(MC2Plants).food(ModFoods.rice)));
    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, magneticraft2.MOD_ID);
    }
    public static void init(ParallelDispatchEvent ev) {

    }


    public static final RegistryObject<hgeneratormultiblocktest> Block_hg_test = BLOCKS.register("hg_test", hgeneratormultiblocktest::new);
    public static final RegistryObject<HeatGeneratorBlock> Block_Heat_Generator = BLOCKS.register("heat_generator", HeatGeneratorBlock::new);
    public static final RegistryObject<Item> HEAT_ITEM = fromBlock(Block_Heat_Generator);
    public static final RegistryObject<BlockEntityType<HeatGeneratorTile>> Tile_Heat_Generator = TILE_ENTITIES.register("heat_generator", () -> BlockEntityType.Builder.of(HeatGeneratorTile::new, Block_Heat_Generator.get()).build(null));

    public static final RegistryObject<CrucibleHeaterBlock> Block_Crucible_Heater = BLOCKS.register ("crucible_heater", CrucibleHeaterBlock::new);
    public static final RegistryObject<Item> CRUCIBLE_HEATER_ITEM = fromBlock(Block_Crucible_Heater);
    public static final RegistryObject<BlockEntityType<CrucibleHeaterTile>> Tile_Crucible_Heater = TILE_ENTITIES.register("crucible_heater", () -> BlockEntityType.Builder.of(CrucibleHeaterTile::new, Block_Crucible_Heater.get()).build(null));





    /**
     * Multiblocks blocks
     */
    public static final RegistryObject<Block> air_lock = registerBlockOnly("air_lock", () -> setUpBlock(new AirLockBlock(Prop.Blocks.BASIC_TECH.get())));

    /**CorePatternSystem**/
    public static final RegistryObject<Block> multiblock = register("multiblock", () -> setUpBlock(new MultiblockBlock(Prop.Blocks.BASIC_TECH.get().noOcclusion())), false);
    public static final RegistryObject<Block> multiblock_master = register("multiblock_master", () -> setUpBlock(new MultiblockBlock(Prop.Blocks.BASIC_TECH.get().noOcclusion())), false);


    public static final RegistryObject<Block> cruciblemb_block = registerBlockOnly("cruciblemb", () -> setUpBlock(new CrucibleMBBlock()));

    /**
     * Multiblocks tiles
     */
    /**CorePatternSystem**/
    public static final RegistryObject<BlockEntityType<MultiblockMasterTile>> MUTIBLOCK_MASTER = TILE_ENTITIES.register("multiblock_master", () ->  registerTiles(MultiblockMasterTile::new, multiblock_master.get()));
    public static final RegistryObject<BlockEntityType<MultiblockTile>> MUTIBLOCK = TILE_ENTITIES.register("multiblock", () ->  registerTiles(MultiblockTile::new, multiblock.get()));


    public static final RegistryObject<BlockEntityType<crucibleBlocktile>> cruciblemb_tile = TILE_ENTITIES.register("cruciblemb_tile", () ->  registerTiles(crucibleBlocktile::new, cruciblemb_block.get()));



    /**
     * Multiblocks items
     */
    /**CorePatternSystem**/

    public static final RegistryObject<Item> cruciblemb_item = ITEMS.register("cruciblemb", () -> createItem(new MultiblockBlockItem(cruciblemb_block.get(), MultiblockPatterns.crucible, Prop.Items.SIXTY_FOUR.get(), "Crucible", "3x3x3")));



    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }
    private static <T extends Block> T setUpBlock(T block) {
        return block;
    }
    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier, CreativeModeTab itemGroup){
        RegistryObject<T> registryObject = BLOCKS.register(id, blockSupplier);
        ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(itemGroup)));
        return registryObject;
    }
    private static <T extends Block> RegistryObject<T> registerBlockOnly(String id, Supplier<T> blockSupplier){
        RegistryObject<T> registryObject = BLOCKS.register(id, blockSupplier);
        return registryObject;
    }
    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier){
        RegistryObject<T> registryObject = BLOCKS.register(id, blockSupplier);

        ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(FinalRegistry.MC2Blocks)));
        return registryObject;
    }
    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier, boolean hasItem){
        RegistryObject<T> registryObject = BLOCKS.register(id, blockSupplier);
        if (hasItem)
            ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(FinalRegistry.MC2Blocks)));
        return registryObject;
    }

    private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
        return (state) -> {
            return state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
        };
    }
    private static <T extends BlockEntity> BlockEntityType<T> registerTiles(BlockEntityType.BlockEntitySupplier<T> tile, Block... validBlock) {
        BlockEntityType<T> type = BlockEntityType.Builder.of(tile, validBlock).build(null);
        for(Block block : validBlock) {
            if(block instanceof BlockMagneticraft2Pattern) {
                ((BlockMagneticraft2Pattern)block).setTileEntity(type);
            }
        }
        return type;
    }
    private static <T extends Item> T createItem(T item) {
        return item;
    }
}
