﻿#pragma checksum "..\..\..\..\Cita\NuevaCita.xaml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "D542B32D774E8D83E3497FF05C29D4F48A43835A"
//------------------------------------------------------------------------------
// <auto-generated>
//     Este código fue generado por una herramienta.
//     Versión de runtime:4.0.30319.42000
//
//     Los cambios en este archivo podrían causar un comportamiento incorrecto y se perderán si
//     se vuelve a generar el código.
// </auto-generated>
//------------------------------------------------------------------------------

using CentroMedico.Cita;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Controls.Ribbon;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;


namespace CentroMedico.Cita {
    
    
    /// <summary>
    /// NuevaCita
    /// </summary>
    public partial class NuevaCita : System.Windows.Window, System.Windows.Markup.IComponentConnector {
        
        
        #line 11 "..\..\..\..\Cita\NuevaCita.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.ComboBox cmbEsp;
        
        #line default
        #line hidden
        
        
        #line 14 "..\..\..\..\Cita\NuevaCita.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.ComboBox cmbMed;
        
        #line default
        #line hidden
        
        
        #line 17 "..\..\..\..\Cita\NuevaCita.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox txbBusq;
        
        #line default
        #line hidden
        
        
        #line 18 "..\..\..\..\Cita\NuevaCita.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnBusq;
        
        #line default
        #line hidden
        
        
        #line 19 "..\..\..\..\Cita\NuevaCita.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Label lblTick;
        
        #line default
        #line hidden
        
        
        #line 22 "..\..\..\..\Cita\NuevaCita.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.DatePicker dtPck;
        
        #line default
        #line hidden
        
        
        #line 24 "..\..\..\..\Cita\NuevaCita.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.ComboBox cmbHorasDisp;
        
        #line default
        #line hidden
        
        
        #line 26 "..\..\..\..\Cita\NuevaCita.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnCrearCita;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "7.0.13.0")]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/CentroMedico;V1.0.0.0;component/cita/nuevacita.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\..\..\Cita\NuevaCita.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "7.0.13.0")]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            this.cmbEsp = ((System.Windows.Controls.ComboBox)(target));
            
            #line 11 "..\..\..\..\Cita\NuevaCita.xaml"
            this.cmbEsp.SelectionChanged += new System.Windows.Controls.SelectionChangedEventHandler(this.cmbEsp_SelectionChanged);
            
            #line default
            #line hidden
            return;
            case 2:
            this.cmbMed = ((System.Windows.Controls.ComboBox)(target));
            
            #line 14 "..\..\..\..\Cita\NuevaCita.xaml"
            this.cmbMed.SelectionChanged += new System.Windows.Controls.SelectionChangedEventHandler(this.cmbMed_SelectionChanged);
            
            #line default
            #line hidden
            return;
            case 3:
            this.txbBusq = ((System.Windows.Controls.TextBox)(target));
            
            #line 17 "..\..\..\..\Cita\NuevaCita.xaml"
            this.txbBusq.TextChanged += new System.Windows.Controls.TextChangedEventHandler(this.txbBusq_TextChanged);
            
            #line default
            #line hidden
            return;
            case 4:
            this.btnBusq = ((System.Windows.Controls.Button)(target));
            
            #line 18 "..\..\..\..\Cita\NuevaCita.xaml"
            this.btnBusq.Click += new System.Windows.RoutedEventHandler(this.btnBusq_Click);
            
            #line default
            #line hidden
            return;
            case 5:
            this.lblTick = ((System.Windows.Controls.Label)(target));
            return;
            case 6:
            this.dtPck = ((System.Windows.Controls.DatePicker)(target));
            
            #line 22 "..\..\..\..\Cita\NuevaCita.xaml"
            this.dtPck.SelectedDateChanged += new System.EventHandler<System.Windows.Controls.SelectionChangedEventArgs>(this.dtPck_SelectedDateChanged);
            
            #line default
            #line hidden
            return;
            case 7:
            this.cmbHorasDisp = ((System.Windows.Controls.ComboBox)(target));
            
            #line 24 "..\..\..\..\Cita\NuevaCita.xaml"
            this.cmbHorasDisp.SelectionChanged += new System.Windows.Controls.SelectionChangedEventHandler(this.cmbHorasDisp_SelectionChanged);
            
            #line default
            #line hidden
            return;
            case 8:
            this.btnCrearCita = ((System.Windows.Controls.Button)(target));
            
            #line 26 "..\..\..\..\Cita\NuevaCita.xaml"
            this.btnCrearCita.Click += new System.Windows.RoutedEventHandler(this.btnCrearCita_Click);
            
            #line default
            #line hidden
            return;
            }
            this._contentLoaded = true;
        }
    }
}

