package main;
import eduni.simjava.*;

public class Client extends Sim_entity {
    private Sim_port in, out;
    private double delay;
    public static int jobs_count = 2;  // Зменшуємо кількість для відповідності UML
    Client(String name, double delay) {
        super(name);
        this.delay = delay;
        in = new Sim_port("In");
        out = new Sim_port("Out");
        add_port(in);
        add_port(out);
    }

    @Override
    public void body() {
        sim_trace(Sim_system.get_trc_level(), "jdl-file has been sent...");
        sim_schedule(out, 3.0, 0); // Відправка JDL-файлу

        for (int i = 0; i < jobs_count; i++) {
            sim_schedule(out, 3.0, i + 1);
            sim_trace(Sim_system.get_trc_level(), "Job " + i + " from client has been sent...");
            sim_pause(delay);
        }

        Sim_event event = new Sim_event();
        sim_wait(event);
        if (event.from_port(in)) {
            sim_trace(Sim_system.get_trc_level(), ">> Result link received!");
            System.out.println("Done!");
            sim_completed(event);
        }
    }
}





