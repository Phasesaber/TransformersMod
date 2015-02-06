package com.transformersmod.update;

public class Update
{
    public String version;
    public String description = ""; // Not required
    public String changelog = ""; // Not required

    public boolean isNewerUpdate(Update update)
    {
        String[] versionChecking = update.version.split("\\.");
        int[] dataChecking = new int[versionChecking.length];
        for (int i = 0 ; i < versionChecking.length; i++) dataChecking[i] = Integer.parseInt(versionChecking[i]); // If someone's stupud and didn't make it a int, just crash.

        String[] versionThis = version.split("\\.");
        int[] dataThis = new int[versionThis.length];
        for (int i = 0; i < versionThis.length; i++) dataThis[i] = Integer.parseInt(versionThis[i]); // Read comment above...

        for (int i = 0; i < dataChecking.length; i++)
        {
            if (dataThis.length >= i) continue;
            if (dataChecking[i] > dataThis[i]) return true; // 1.0.1 > 1.0.0
        }

        return false;
    }

    public String toString()
    {
        return "{\"version\":\"" + version + "\",\"description:\":\"" + description + "\",\"changelog:\":\"" + changelog + "\"}";
    }
}
