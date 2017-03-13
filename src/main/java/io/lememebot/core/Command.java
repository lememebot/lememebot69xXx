package io.lememebot.core;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.core
 * Created by Guy on 12-Mar-17.
 * <p>
 * Description:
 */
public class Command {
    private final String EMPTY="";
    private final String m_cmdPrefix;
    private final int m_numParams;
    private String m_cmd;
    private String[] m_params;

    public Command(String strCmdPrefix,int cmdNumParameters)
    {
        m_cmdPrefix = strCmdPrefix;
        m_numParams = cmdNumParameters;
    }

    public Command(String strCmdPrefix)
    {
        this(strCmdPrefix,-1);
    }

    // Return true if the command is valid for this pattern
    boolean parse(String message)
    {
        if(isEmptyPrefix() || message.startsWith(m_cmdPrefix)) {

            if(m_numParams > 0) {
                // Example: !(RemindMe) (thid should be parsed as one param)
                m_params = message.split(" ", m_numParams);
            } else {
                m_params = message.split(" ");
            }

            m_cmd = m_params[0].substring(m_cmdPrefix.length());

            return true;
        }

        return false;
    }

    private boolean isEmptyPrefix()
    {
        return m_cmdPrefix.isEmpty();
    }

    public String getPrefix()
    {
        return m_cmdPrefix;
    }

    public String getCommand()
    {
        return m_cmd;
    }

    // Index: 1-n
    public String getParameter(int index)
    {
        if (index > 0 && index < m_params.length)
            return m_params[index];

        return EMPTY;
    }
}
