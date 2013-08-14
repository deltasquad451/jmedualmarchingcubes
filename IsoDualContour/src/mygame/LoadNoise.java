/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import mygame.source.ShortGridSource;

/**
 *
 * @author Karsten
 */
public class LoadNoise {
    
    public static void loadNoise(int locX, int locY, int locZ, int sizeX, int sizeY, int sizeZ, float roughness, ShortGridSource source) { 

        OldNoise noise = new OldNoise(null, roughness, sizeX, sizeZ);
        noise.initialise();
        float gridMinimum = noise.getMinimum();
        float gridLargestDifference = noise.getMaximum() - gridMinimum;
        float[][] grid = noise.getGrid();

        float[][] newGrid = new float[grid.length][grid[0].length];

        for (int x = 0; x < grid.length; x++) {
            float[] row = grid[x];
            for (int z = 0; z < row.length; z++) {
                float blockHeight = ((row[z] - gridMinimum) / gridLargestDifference * sizeY) + 1;
                
                
                newGrid[x][z] = blockHeight;
            }
        }

        for (int x = 0; x < grid.length; x++) {
            float[] row = grid[x];
            for (int z = 0; z < row.length; z++) {
                int blockHeight = (int)newGrid[x][z];
                
                for (int y = 0; y < blockHeight-1; y++) {
                    source.setVolumeGridValue(locX + x, locY + y, locZ + z, (short)1);
                    
                    if(y == blockHeight-1)
                    {
                        float rest=newGrid[x][z]-(float)blockHeight;
                        source.setVolumeGridValue(locX + x, locY + y, locZ + z, (short)rest);
                    }
                }
                
            }
        }
    }
}
