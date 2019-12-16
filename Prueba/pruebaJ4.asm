	.data
_p:	.word 0
_a:	.word 0
_b:	.word 0
__msg0:	.asciiz "\n antes: "
__msg1:	.asciiz "ingrese puntos: "
__msg2:	.asciiz "ingrese inicial: "
__msg3:	.asciiz "\n desps: "

	.text
	.globl main
_retpro_pro: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	move $fp, $sp
	sub $sp, $sp, 12
	move $s0, $a0
	li $v0, 9
	li $a0, 8
	syscall
	sw $v0, 0($sp)
	li $v0, 4
	la $a0, __msg0
	syscall
	li $v0, 1
	lw $a0, 0($s0)
	syscall
	li $v0, 4
	la $a0, __msg0
	syscall
	li $v0, 11
	lw $a0, 4($s0)
	syscall
	lw $t0, _-100
	lw $s7, 0($sp)
	sw $t0, 0($s7)
	li $t0, 'j'
	lw $s7, 0($sp)
	sw $t0, 4($s7)
	lw $t0, 0($sp)
	move $v0, $t0
	b _retpro_pro_END

_retpro_pro_END:
	move $sp, $fp
	lw $ra, -8($sp)
	lw $fp, -4($sp)
	jr $ra

main:
	move $fp, $sp
	li $v0, 9
	li $a0, 8
	syscall
	sw $v0, _p
	li $v0, 4
	la $a0, __msg1
	syscall
	lw $s6, _p
	li $v0, 5
	syscall
	sw $v0, 0($s6)
	li $v0, 4
	la $a0, __msg2
	syscall
	lw $s6, _p
	li $v0, 12
	syscall
	sw $v0, 4($s6)
	sw $t9, -4($sp)
	sw $s6, -8($sp)
	sw $s7, -12($sp)
	sub $sp, $sp, 12
	lw $s7, _p
	move $a0, $s7
	jal _retpro_pro
	add $sp, $sp, 12
	lw $s7, -12($sp)
	lw $s6, -8($sp)
	lw $t9, -4($sp)
	move $t0, $v0
	sw $t0, _p
	li $v0, 4
	la $a0, __msg3
	syscall
	lw $s6, _p
	li $v0, 1
	lw $a0, 0($s6)
	syscall
	li $v0, 4
	la $a0, __msg3
	syscall
	lw $s6, _p
	li $v0, 11
	lw $a0, 4($s6)
	syscall
etiq0:
	li $v0, 10
	syscall