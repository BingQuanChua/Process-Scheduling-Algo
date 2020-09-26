package os;

import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class GanttChart extends JPanel {

	private List<ProcessOutput> timeline;
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if (timeline != null)
        {
        	int previousWidth = 0;
            for (int i = 0; i < timeline.size(); i++)
            {
            	ProcessOutput event = timeline.get(i);
            	int width = 30 * (event.getFinishTime() - event.getStartTime() - 1);
                int locationX = 30 * (i + 1) + previousWidth;
                int y = 40;
                
                g.drawRect(locationX, y, 30 + width, 30);
                g.setFont(new Font("Segoe UI", Font.BOLD, 13));
                g.drawString(event.getProcessName(), locationX + 10, y + 20);
                g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g.drawString(Integer.toString(event.getStartTime()), locationX - 5, y + 45);
                
                if (i == timeline.size() - 1)
                {
                    g.drawString(Integer.toString(event.getFinishTime()), locationX + width + 27, y + 45);
                }
                
                previousWidth += width;
            }

        }
    }
    
    public void setTimeline(List<ProcessOutput> timeline)
    {
        this.timeline = timeline;
        repaint();
    }
}
