package fx7.r2r;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import fx7.r2r.itemlistener.WorldCoordinates;
import fx7.r2r.itemlistener.lever.LeverConfig;
import fx7.r2r.itemlistener.lever.OpenHabLeverConfig;
import fx7.r2r.rest.RestStub;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RedstoneTwoRestPluginTest extends TestCase
{
	public RedstoneTwoRestPluginTest(String testName)
	{
		super(testName);
	}

	public static Test suite()
	{
		return new TestSuite(RedstoneTwoRestPluginTest.class);
	}

	public void testConfiguration() throws IOException
	{
		ConfigurationSerialization.registerClass(OpenHabLeverConfig.class);
		ConfigurationSerialization.registerClass(WorldCoordinates.class);
		ConfigurationSerialization.registerClass(RestStub.class);
		FileConfiguration conf = YamlConfiguration.loadConfiguration(new File("test/data/config.yml"));

		List<LeverConfig> levers = (List<LeverConfig>) conf.getList("levers");
		assertNotNull(levers);
		assertEquals(1, levers.size());

		List<RestStub> stubs = (List<RestStub>) conf.getList("restStubs");
		assertNotNull(stubs);
		assertEquals(1, stubs.size());

		LeverConfig leverConfig = levers.get(0);
		assertTrue(leverConfig instanceof OpenHabLeverConfig);
		RestStub stub = stubs.get(0);
		
		int httpStatusCode;
		httpStatusCode = stub.call(leverConfig, getBlock(false));
		assertEquals(200, httpStatusCode);
		httpStatusCode = stub.call(leverConfig, getBlock(true));
		assertEquals(200, httpStatusCode);
	}
	
	private Block getBlock(boolean powered)
	{
		return new Block()
		{
			
			@Override
			public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeMetadata(String metadataKey, Plugin owningPlugin)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean hasMetadata(String metadataKey)
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public List<MetadataValue> getMetadata(String metadataKey)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setType(Material type, boolean applyPhysics)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setType(Material type)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setBlockData(BlockData data, boolean applyPhysics)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setBlockData(BlockData data)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setBiome(Biome bio)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance,
					FluidCollisionMode fluidCollisionMode)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isPassable()
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isLiquid()
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isEmpty()
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isBlockPowered()
			{
				return powered;
			}
			
			@Override
			public boolean isBlockIndirectlyPowered()
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isBlockFacePowered(BlockFace face)
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isBlockFaceIndirectlyPowered(BlockFace face)
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public int getZ()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getY()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getX()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public World getWorld()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Material getType()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public double getTemperature()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public BlockState getState()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getRelative(int modX, int modY, int modZ)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getRelative(BlockFace face, int distance)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Block getRelative(BlockFace face)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PistonMoveReaction getPistonMoveReaction()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Location getLocation(Location loc)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Location getLocation()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public byte getLightLevel()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public byte getLightFromSky()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public byte getLightFromBlocks()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getHumidity()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public BlockFace getFace(Block block)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<ItemStack> getDrops(ItemStack tool, Entity entity)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<ItemStack> getDrops(ItemStack tool)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<ItemStack> getDrops()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public byte getData()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Chunk getChunk()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public BoundingBox getBoundingBox()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getBlockPower(BlockFace face)
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getBlockPower()
			{
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public BlockData getBlockData()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Biome getBiome()
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean breakNaturally(ItemStack tool)
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean breakNaturally()
			{
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean applyBoneMeal(BlockFace face)
			{
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
}
