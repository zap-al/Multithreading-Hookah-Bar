package com.mrmrmr7.task.entity;

import com.google.gson.annotations.SerializedName;
import com.mrmrmr7.task.service.FreeHookahNotFoundException;
import com.mrmrmr7.task.service.HookahBarService;
import com.mrmrmr7.task.util.AppConstant;
import com.mrmrmr7.task.util.StringPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Objects;

public class SingleClient implements Client, Runnable {
    private Logger logger;

    @SerializedName("name")
    private String name;

    @SerializedName("wantHookah")
    private Boolean wantHookah;

    @SerializedName("hookahName")
    private String hookahName;

    public SingleClient(String name, Boolean wantHookah, String hookahName) {
        this.logger = LogManager.getLogger(SingleClient.class);
        this.name = name;
        this.wantHookah = wantHookah;
        this.hookahName = hookahName;
    }

    public SingleClient(String name, Boolean wantHookah) {
        this(name, wantHookah, "any");
    }

    @Override
    public void run() {
        logger.info(MessageFormat.format(StringPool.FIND_BAR, name));
        HookahBarService hookahBar = new HookahBarService();

        try {
            if(hookahBar.tryAddBarClient()) {
                logger.info(MessageFormat.format(StringPool.COME_TO_BAR, name));
            } else {
                logger.warn(name + "Too much time wait for hookah!!");
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
            logger.error("Interrupted exception fouid");
        }

            try {
                if (wantHookah) {
                    int freeHookahNum = 0;
                    freeHookahNum = hookahBar.getFreeHookah(this.hookahName);
                    logger.info(MessageFormat.format(StringPool.GET_HOOKAH, name, hookahBar.getHookahNameByNum(freeHookahNum)));
                    Thread.sleep(AppConstant.TIME_OF_SMOKE);
                    logger.info(MessageFormat.format(StringPool.RETURN_HOOKAH, name, hookahBar.getHookahNameByNum(freeHookahNum)));
                    hookahBar.removeHookah(freeHookahNum);
                }
            } catch (InterruptedException e) {
                logger.error("Interrupted exception found");
            } catch (FreeHookahNotFoundException e) {
                logger.error("Free hookah not found");
            }


        hookahBar.removeBarClient();
        logger.info(MessageFormat.format(StringPool.LEAVE_BAR, name));
        logger.info(MessageFormat.format(StringPool.IN_BAR_NOW, hookahBar.getClientsInBarNow()));
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SingleClient{name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleClient singleClient = (SingleClient) o;
        return Objects.equals(logger, singleClient.logger) &&
                Objects.equals(name, singleClient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logger, name);
    }
}