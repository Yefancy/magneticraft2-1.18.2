package com.magneticraft2.common.utils.mbnew;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import com.magneticraft2.common.utils.DirectionUtils;
import com.mojang.math.Transformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class mgc2Properties
{
    public static final DirectionProperty FACING_ALL = DirectionProperty.create("facing", DirectionUtils.VALUES);
    public static final DirectionProperty FACING_HORIZONTAL = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final DirectionProperty FACING_TOP_DOWN = DirectionProperty.create("facing", Direction.UP, Direction.DOWN);

    public static final BooleanProperty MULTIBLOCKSLAVE = BooleanProperty.create("multiblockslave");
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty MIRRORED = BooleanProperty.create("mirrored");

    public static final IntegerProperty INT_16 = IntegerProperty.create("int_16", 0, 15);
    public static final IntegerProperty INT_32 = IntegerProperty.create("int_32", 0, 31);

    public static record VisibilityList(Set<String> selected, boolean showSelected)
    {
        private VisibilityList(Collection<String> selected, boolean show)
        {
            this(ImmutableSet.copyOf(selected), show);
        }

        public static VisibilityList show(String... visible)
        {
            return show(Arrays.asList(visible));
        }

        public static VisibilityList show(Collection<String> visible)
        {
            return new VisibilityList(visible, true);
        }

        public static VisibilityList hide(Collection<String> hidden)
        {
            return new VisibilityList(hidden, false);
        }

        public static VisibilityList showAll()
        {
            return hide(ImmutableSet.of());
        }

        public static VisibilityList hideAll()
        {
            return show(ImmutableList.of());
        }

        public boolean isVisible(String group)
        {
            return showSelected==selected.contains(group);
        }
    }

    public static record IEObjState(VisibilityList visibility, Transformation transform)
    {
        public IEObjState(VisibilityList visibility)
        {
            this(visibility, Transformation.identity());
        }
    }

    public static class Model
    {
        @Deprecated
        public static final ModelProperty<IEObjState> IE_OBJ_STATE = new ModelProperty<>();
        @Deprecated
        public static final ModelProperty<Map<String, String>> TEXTURE_REMAP = new ModelProperty<>();
        public static final ModelProperty<BlockPos> SUBMODEL_OFFSET = new ModelProperty<>();
    }
}
