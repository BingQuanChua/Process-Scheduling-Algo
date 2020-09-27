package os;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class GanttChart extends JPanel {

    private static final long serialVersionUID = 1L;
    private List<ProcessOutput> processOutputList;
    boolean clearPane;
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if (processOutputList != null && clearPane)
        {
        	int previousWidth = 0;
            for (int i = 0; i < processOutputList.size(); i++)
            {
            	ProcessOutput event = processOutputList.get(i);
            	int width = 30 * (event.getFinishTime() - event.getStartTime() - 1);
                int locationX = 30 * (i + 1) + previousWidth;
                int y = 40;
                
                g.drawRect(locationX, y, 30 + width, 30);
                g.setFont(new Font("Segoe UI", Font.BOLD, 13));
                g.drawString(event.getProcessName(), locationX + 10, y + 20);
                g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g.drawString(Integer.toString(event.getStartTime()), locationX - 5, y + 45);
                
                if (i == processOutputList.size() - 1)
                {
                    g.drawString(Integer.toString(event.getFinishTime()), locationX + width + 27, y + 45);
                }

                previousWidth += width;
            }
        } else {       
            g.setColor(Color.WHITE);
            g.fillOval(0, 0, 600, 200);
        }
    }
    
    public void setProcessOutputList(List<ProcessOutput> processOutputList)
    {
        this.processOutputList = processOutputList;
        this.clearPane = true;
        // each time box is 30 pixels width, spacing at front and back = 60
		setPreferredSize(new Dimension(processOutputList.get(processOutputList.size()-1).getFinishTime()*30 + 60, 195));
		repaint();
		revalidate();
    }
    
    public void clearGanttChart()
    {
        this.clearPane = false;
		repaint();
		revalidate();
    }

}
