package com.example.demo.controller;

import com.example.demo.model.Cuenta;
import com.example.demo.model.OperacionBancaria;
import com.example.demo.model.Usuario;
import com.example.demo.repository.CuentaRepository;
import com.example.demo.repository.OperacionBancariaRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.OperacionBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CuentaController {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private OperacionBancariaRepository operacionBancariaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OperacionBancariaService operacionBancariaService;

    @GetMapping("/cuentas")
    public String getCuentas(Model model) {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        model.addAttribute("cuentas", cuentas);
        return "cuentas";
    }

    @PostMapping("/cuentas/toggleEstado")
    public String toggleEstado(@RequestParam("numeroDeCuenta") Long numeroDeCuenta, Boolean estado, RedirectAttributes redirectAttributes) {

        cuentaRepository.updateEstado(numeroDeCuenta, estado);
        return "redirect:/cuentas";
    }

    @GetMapping("/cuentas/new")
    public String newCuenta(Model model) {
        Cuenta cuenta = new Cuenta();
        List<Usuario> usuarios = new ArrayList<>(usuarioRepository.findAll());
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("usuarios", usuarios);
        return "cuentaNueva";
    }

    @PostMapping("/cuentas/save")
    public String insertCuenta(@ModelAttribute("cuenta") Cuenta cuenta, RedirectAttributes redirectAttributes) {
        cuentaRepository.insertCuenta(cuenta.getTipoCuenta().name(),cuenta.getSaldo(),
                cuenta.getUltimaTransaccion(),cuenta.getEstado(),cuenta.getUsuario().getId());
        //cuentaRepository.save(cuenta);
        redirectAttributes.addFlashAttribute("cuenta", "The cuenta has been saved successfully");
        return "redirect:/cuentas/new";
    }

    @GetMapping("/extracto")
    public String showBankStatementForm(Model model) {
        // Populate cuentas and add to model
        List<Cuenta> cuentas = cuentaRepository.findAll();
        model.addAttribute("cuentas", cuentas);
        return "extractoNuevo"; // Return the HTML file name
    }

    @GetMapping("/extracto/serializable")
    public String extractoSerializable(Model model){
        List<Cuenta> cuentas = cuentaRepository.findAll();
        model.addAttribute("cuentas", cuentas);
        return "extractoSerializable";
    }

    @PostMapping("/extracto/generate/serializable")
    public String generateExtractoSerializable(@RequestParam(name = "numeroDeCuenta")Long numeroDeCuenta, Model model) {
        LocalDateTime date = LocalDateTime.now();

        Cuenta cuenta = cuentaRepository.findByNumeroDeCuenta(numeroDeCuenta);

        List<OperacionBancaria> operaciones = operacionBancariaService.findOperacionesSerializable(numeroDeCuenta);;

        Float initialBalance = cuenta.getSaldo();


        for (OperacionBancaria operacion : operaciones) {
            if (operacion.getTipoOperacion().equals("consignacion")) {
                initialBalance -= operacion.getValor();
            } else if (operacion.getTipoOperacion().equals("transferencia") || operacion.getTipoOperacion().equals("retiro")) {
                initialBalance += operacion.getValor();
            }
        }

        List<String> statement = new ArrayList<>();
        statement.add("Tipo Operacion   -   Fecha   -   Monto");
        for (OperacionBancaria operacion : operaciones) {
            String operationType = operacion.getTipoOperacion();
            statement.add(operationType + " - " + operacion.getFecha() + " - " + operacion.getValor());
        }


        Float finalBalance = cuenta.getSaldo();

        model.addAttribute("cuentas", cuentaRepository.findAll());
        model.addAttribute("accountNumber", numeroDeCuenta);
        model.addAttribute("month", date);
        model.addAttribute("initialBalance", initialBalance);
        model.addAttribute("statement", statement);
        model.addAttribute("finalBalance", finalBalance);
        // Add other attributes as needed

        return "extracto";
    }

    @GetMapping("/extracto/read")
    public String extractoRead(Model model){
        List<Cuenta> cuentas = cuentaRepository.findAll();
        model.addAttribute("cuentas", cuentas);
        return "extractoRead";
    }

    @PostMapping("/extracto/generate/read")
    public String generateExtractoRead(@RequestParam(name = "numeroDeCuenta")Long numeroDeCuenta, Model model) {
        LocalDateTime date = LocalDateTime.now();

        Cuenta cuenta = cuentaRepository.findByNumeroDeCuenta(numeroDeCuenta);

        List<OperacionBancaria> operaciones = operacionBancariaService.findOperacionesRead(numeroDeCuenta);

        Float initialBalance = cuenta.getSaldo();


        for (OperacionBancaria operacion : operaciones) {
            if (operacion.getTipoOperacion().equals("consignacion")) {
                initialBalance -= operacion.getValor();
            } else if (operacion.getTipoOperacion().equals("transferencia") || operacion.getTipoOperacion().equals("retiro")) {
                initialBalance += operacion.getValor();
            }
        }

        List<String> statement = new ArrayList<>();
        statement.add("Tipo Operacion   -   Fecha   -   Monto");
        for (OperacionBancaria operacion : operaciones) {
            String operationType = operacion.getTipoOperacion();
            statement.add(operationType + " - " + operacion.getFecha() + " - " + operacion.getValor());
        }


        Float finalBalance = cuenta.getSaldo();

        model.addAttribute("cuentas", cuentaRepository.findAll());
        model.addAttribute("accountNumber", numeroDeCuenta);
        model.addAttribute("month", date);
        model.addAttribute("initialBalance", initialBalance);
        model.addAttribute("statement", statement);
        model.addAttribute("finalBalance", finalBalance);
        // Add other attributes as needed

        return "extracto";
    }

    @PostMapping("/extracto/generate")
    public String generateStatement(@RequestParam(name = "numeroDeCuenta", required = false) Long numeroDeCuenta,
                                    @RequestParam(name = "month", required = false) int month,
                                    Model model) {
        // Implement the logic to generate the bank statement for the given account number and month
        // Fetch the account details and operations for the specified account number and month
        // Calculate initial balance, list of operations, and balance at the end of the month
        // 1. Retrieve the account details based on the provided account number
        Cuenta cuenta = cuentaRepository.findByNumeroDeCuenta(numeroDeCuenta);

        // 2. Retrieve all banking operations for the specified account and month
        List<OperacionBancaria> operaciones = operacionBancariaRepository.findByCuentaOrigenNumeroDeCuentaAndFechaBetween(numeroDeCuenta,
                        parseMonthStart(month),
                        parseMonthEnd(month));

        // 3. Calculate the initial balance before the beginning of the month
        Float initialBalance = cuenta.getSaldo();

        // 4. Iterate through each banking operation to adjust the balance
        for (OperacionBancaria operacion : operaciones) {
            if (operacion.getTipoOperacion().equals("consignacion")) {
                // If it's a deposit, increase the balance
                initialBalance -= operacion.getValor();
            } else if (operacion.getTipoOperacion().equals("transferencia") || operacion.getTipoOperacion().equals("retiro")) {
                // If it's a transfer or withdrawal, decrease the balance
                initialBalance += operacion.getValor();
            }
        }

        // 5. Summarize the operations (deposits, withdrawals, transfers) along with their dates
        List<String> statement = new ArrayList<>();
        statement.add("Tipo Operacion   -   Fecha   -   Monto");
        for (OperacionBancaria operacion : operaciones) {
            String operationType = operacion.getTipoOperacion();
            statement.add(operationType + " - " + operacion.getFecha() + " - " + operacion.getValor());
        }

        // 6. Calculate the balance at the end of the month
        Float finalBalance = cuenta.getSaldo();
        // Pass the statement data to the view
        model.addAttribute("cuentas", cuentaRepository.findAll());
        model.addAttribute("accountNumber", numeroDeCuenta);
        model.addAttribute("month", month);
        model.addAttribute("initialBalance", initialBalance);
        model.addAttribute("statement", statement);
        model.addAttribute("finalBalance", finalBalance);
        // Add other attributes as needed

        return "extracto"; // Return the view name
    }

    // Helper method to parse the start of the month
    private LocalDateTime parseMonthStart(int month) {
        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Create a LocalDate object for the first day of the specified month
        LocalDate firstDayOfMonth = LocalDate.of(currentYear, month, 1);

        // Set the time to the beginning of the day
        return firstDayOfMonth.atStartOfDay();
    }

    // Helper method to parse the end of the month
    private LocalDateTime parseMonthEnd(int month) {
        // Get the current year
        int currentYear = LocalDate.now().getYear();

        // Create a LocalDate object for the last day of the specified month
        LocalDate lastDayOfMonth = LocalDate.of(currentYear, month, 1).plusMonths(1).minusDays(1);

        // Set the time to the end of the day
        return lastDayOfMonth.atTime(23, 59, 59);
    }





}
