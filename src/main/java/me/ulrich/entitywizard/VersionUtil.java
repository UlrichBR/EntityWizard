package me.ulrich.entitywizard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.*;
import org.jetbrains.annotations.*;

import com.google.common.primitives.Ints;

public class VersionUtil {

    public static String VERSION;

    public static String CLEAN_VERSION;
    
    public static Integer BUILD_VERSION;

    static {
        VERSION = "v"+String.valueOf(getCurrentVersion());

        CLEAN_VERSION = VERSION.substring(0, VERSION.length() - 1);
        
        BUILD_VERSION = 0;
        
        BUILD_VERSION = Ints.tryParse(VERSION.substring(VERSION.length()-1, VERSION.length()));
		if (BUILD_VERSION == null) {
			BUILD_VERSION = 0;
		}

        
    }
    
    private static int getCurrentVersion() {
		Matcher matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?").matcher(Bukkit.getBukkitVersion());
		StringBuilder stringBuilder = new StringBuilder();
		if (matcher.find()) {
			stringBuilder.append(matcher.group("version").replace(".", ""));
			String patch = matcher.group("patch");
			if (patch == null) {
				stringBuilder.append("0");
			} else {
				stringBuilder.append(patch.replace(".", ""));
			} 
		} 
		Integer version = Ints.tryParse(stringBuilder.toString());
		if (version == null) {
			return 0;
		}

		return version.intValue();
	}
    
    public static boolean is(@NotNull VersionEnum ve) {
        return ve.getOrder()==VersionEnum.valueOf(CLEAN_VERSION.toUpperCase()).getOrder();
    }
    

    public static boolean isCompatible(@NotNull VersionEnum ve){
        return VERSION.toLowerCase().contains(ve.toString().toLowerCase());
    }

    public static boolean isAbove(@NotNull VersionEnum ve) {
        return VersionEnum.valueOf(CLEAN_VERSION.toUpperCase()).getOrder() >= ve.getOrder();
    }

    public static boolean isBelow(@NotNull VersionEnum ve) {
        return VersionEnum.valueOf(CLEAN_VERSION.toUpperCase()).getOrder() <= ve.getOrder();
    }

    public static boolean isBetween(@NotNull VersionEnum ve1, @NotNull VersionEnum ve2) {
        return isAbove(ve1) && isBelow(ve2);
    }


    public enum VersionEnum {

       	V17(0),
        V18(1),
        V19(2),
        V110(3),
        V111(4),
        V112(5),
        V113(6),
        V114(7),
        V115(8),
        V116(9),
        V117(10),
        V118(11),
    	V119(12),
    	V120(13),
    	V121(14);

        private int order;

        VersionEnum(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }

    }
}
