package simulation;import java.awt.*;import java.awt.event.*;/** Simule la fente de l'enveloppe */class SimFenteEnveloppe extends Button{    /** Constructor     */    SimFenteEnveloppe()    {        super("Cliquer pour inserer envelope");        addActionListener(new ActionListener() {            public void actionPerformed(ActionEvent e)            {                synchronized(SimFenteEnveloppe.this)                {                    inserted = true;                    SimFenteEnveloppe.this.notify();                }            }        });    }        /** Simule l'acceptation d'une enveloppe du client     *     *  return true si l'enveloppe est inseree dans le temps alloue     */    public synchronized boolean acceptEnvelope()    {        inserted = false;        setVisible(true);                            try        {             wait(MAXIMUM_WAIT_TIME);        }        catch(InterruptedException e)        { }                if (inserted)        {            // Animate envelope going into the machine                        Rectangle originalBounds = getBounds();                        Rectangle currentBounds =               new Rectangle(originalBounds.x, originalBounds.y,                             originalBounds.width, originalBounds.height);                                         while (currentBounds.width > 0 && currentBounds.height > 0)            {                 setBounds(currentBounds.x, currentBounds.y,                        currentBounds.width, currentBounds.height);                repaint();                try                 {                     Thread.sleep(100);                }                 catch (InterruptedException e)                 { }                currentBounds.height -= 1;                currentBounds.width =                   (originalBounds.width * currentBounds.height) / originalBounds.height;                currentBounds.x =                  originalBounds.x + (originalBounds.width - currentBounds.width) / 2;                currentBounds.y =                  originalBounds.y + (originalBounds.height - currentBounds.height) / 2;            }                        setVisible(false);            setBounds(originalBounds);        }        else        {            setVisible(false);        }        return inserted;    }    /** Informe que le client a appuyer sur annuler     */    public synchronized void cancelRequested()    {        notify();           }        /** Devient vrai lorsque l'enveloppe est inseree     */    private boolean inserted;        /** Temps maximum d'attente pour insertion d'enveloppe -  en millisecondes     */    private static long MAXIMUM_WAIT_TIME = 20 * 1000;}                                       