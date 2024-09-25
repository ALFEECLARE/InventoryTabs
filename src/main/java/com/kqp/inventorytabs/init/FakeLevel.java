package com.kqp.inventorytabs.init;

import java.util.List;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LightChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.ScheduledTick;

public class FakeLevel extends Level implements LightChunkGetter {

    public FakeLevel() {
        super(new FakeWritableLevelData(), ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld")), Minecraft.getInstance().level.registryAccess(), Minecraft.getInstance().level.dimensionTypeRegistration(), () -> Minecraft.getInstance().getProfiler(), true, false, 91247917248L, 0);
    }

    @Override
    public void sendBlockUpdated(BlockPos pPos, BlockState pOldState, BlockState pNewState, int pFlags) {

    }

    @Override
    public void playSeededSound(@Nullable Player p_262953_, double p_263004_, double p_263398_, double p_263376_, Holder<SoundEvent> p_263359_, SoundSource p_263020_, float p_263055_, float p_262914_, long p_262991_) {

    }

    @Override
    public void playSeededSound(@Nullable Player pPlayer, double pX, double pY, double pZ, SoundEvent pSoundEvent, SoundSource pSoundSource, float pVolume, float pPitch, long pSeed) {

    }

    @Override
    public void playSeededSound(@Nullable Player p_220372_, Entity p_220373_, Holder<SoundEvent> p_263500_, SoundSource p_220375_, float p_220376_, float p_220377_, long p_220378_) {

    }

    @NotNull
    @Override
    public String gatherChunkSourceStats() {
        return "null";
    }

    @Nullable
    @Override
    public Entity getEntity(int pId) {
        return null;
    }

    @Nullable
    @Override
    public MapItemSavedData getMapData(MapId pMapName) {
        return null;
    }

    @Override
    public void setMapData(MapId pMapId, MapItemSavedData pData) {

    }

    @Override
    public MapId getFreeMapId() {
        return new MapId(0);
    }

    @Override
    public void destroyBlockProgress(int pBreakerId, BlockPos pPos, int pProgress) {

    }

    @NotNull
    @Override
    public Scoreboard getScoreboard() {
        return new Scoreboard();
    }

    @NotNull
    @Override
    public RecipeManager getRecipeManager() {
        return new RecipeManager(null);
    }

    @NotNull
    @Override
    protected LevelEntityGetter<Entity> getEntities() {
        return new LevelEntityGetter<Entity>() {
            @Nullable
            @Override
            public Entity get(int pId) {
                return null;
            }

            @Nullable
            @Override
            public Entity get(UUID pUuid) {
                return null;
            }

            @NotNull
            @Override
            public Iterable<Entity> getAll() {
                return List.of();
            }

            @Override
            public <U extends Entity> void get(EntityTypeTest<Entity, U> p_156935_, AbortableIterationConsumer<U> p_261602_) {

            }

            @Override
            public void get(AABB pBoundingBox, Consumer<Entity> pConsumer) {

            }

            @Override
            public <U extends Entity> void get(EntityTypeTest<Entity, U> p_156932_, AABB p_156933_, AbortableIterationConsumer<U> p_261542_) {

            }
        };
    }

    @NotNull
    @Override
    public LevelTickAccess<Block> getBlockTicks() {
        return new LevelTickAccess<>() {
            @Override
            public boolean willTickThisTick(BlockPos p_193197_, Block p_193198_) {
                return false;
            }

            @Override
            public void schedule(ScheduledTick<Block> pTick) {

            }

            @Override
            public boolean hasScheduledTick(BlockPos p_193429_, Block p_193430_) {
                return false;
            }

            @Override
            public int count() {
                return 0;
            }
        };
    }

    @NotNull
    @Override
    public LevelTickAccess<Fluid> getFluidTicks() {
        return new LevelTickAccess<>() {
            @Override
            public boolean willTickThisTick(BlockPos p_193197_, Fluid p_193198_) {
                return false;
            }

            @Override
            public void schedule(ScheduledTick<Fluid> pTick) {

            }

            @Override
            public boolean hasScheduledTick(BlockPos p_193429_, Fluid p_193430_) {
                return false;
            }

            @Override
            public int count() {
                return 0;
            }
        };
    }

    @NotNull
    @Override
    public ChunkSource getChunkSource() {
        return new ChunkSource() {
            @Nullable
            @Override
            public ChunkAccess getChunk(int pChunkX, int pChunkZ, ChunkStatus pRequiredStatus, boolean pLoad) {
                return null;
            }

            @Override
            public void tick(BooleanSupplier pHasTimeLeft, boolean pTickChunks) {

            }

            @NotNull
            @Override
            public String gatherStats() {
                return "null";
            }

            @Override
            public int getLoadedChunksCount() {
                return 0;
            }

            @NotNull
            @Override
            public LevelLightEngine getLightEngine() {
                return new LevelLightEngine(FakeLevel.this, false, false);
            }

            @NotNull
            @Override
            public BlockGetter getLevel() {
                return FakeLevel.this;
            }
        };
    }

    @Override
    public void levelEvent(@Nullable Player pPlayer, int pType, @NotNull BlockPos pPos, int pData) {

    }

    //gameEvent(@Nullable Entity pEntity, Holder<GameEvent> pGameEvent, Vec3 pPos) {

    @Override
    public void gameEvent(@NotNull Holder<GameEvent> pEvent, @NotNull Vec3 pPosition, @NotNull GameEvent.Context pContext) {

    }

    @NotNull
    @Override
    public RegistryAccess registryAccess() {
        return RegistryAccess.EMPTY;
    }

    @NotNull
    @Override
    public FeatureFlagSet enabledFeatures() {
        return Minecraft.getInstance().level.enabledFeatures();
    }

    @Override
    public float getShade(@NotNull Direction pDirection, boolean pShade) {
        return 0;
    }

    @NotNull
    @Override
    public List<? extends Player> players() {
        return List.of();
    }

    @NotNull
    @Override
    public Holder<Biome> getUncachedNoiseBiome(int pX, int pY, int pZ) {
        return registryAccess().registry(Registries.BIOME).get().getHolderOrThrow(Biomes.BADLANDS);
    }

    @Nullable
    @Override
    public LightChunk getChunkForLighting(int pChunkX, int pChunkZ) {
        return null;
    }

    @NotNull
    @Override
    public BlockGetter getLevel() {
        return this;
    }

    static class FakeWritableLevelData implements WritableLevelData {

    	@Override
    	public void setSpawn(BlockPos pSpawnPoint, float pSpawnAngle) {
    	}

    	public BlockPos getSpawnPos() {
    		return new BlockPos(0,0,0);
    	}

        @Override
        public float getSpawnAngle() {
            return 0;
        }

        @Override
        public long getGameTime() {
            return 0;
        }

        @Override
        public long getDayTime() {
            return 0;
        }

        @Override
        public boolean isThundering() {
            return false;
        }

        @Override
        public boolean isRaining() {
            return false;
        }

        @Override
        public void setRaining(boolean pRaining) {
        }

        @Override
        public boolean isHardcore() {
            return false;
        }

        @NotNull
        @Override
        public GameRules getGameRules() {
            return new GameRules();
        }

        @NotNull
        @Override
        public Difficulty getDifficulty() {
            return Difficulty.EASY;
        }

        @Override
        public boolean isDifficultyLocked() {
            return false;
        }
    }

	@Override
	public TickRateManager tickRateManager() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public PotionBrewing potionBrewing() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setDayTimeFraction(float dayTimeFraction) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public float getDayTimeFraction() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public float getDayTimePerTick() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void setDayTimePerTick(float dayTimePerTick) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
